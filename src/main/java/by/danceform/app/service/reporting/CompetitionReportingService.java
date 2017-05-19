package by.danceform.app.service.reporting;

import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.service.competition.CompetitionService;
import by.danceform.app.service.couple.RegisteredCoupleService;
import by.danceform.app.web.rest.util.DownloadService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by USER on 22.02.2017.
 */
@Service
public class CompetitionReportingService {

    private static final int SHEET_NAME_LIMITATION = 31;

    @Inject
    private CompetitionService competitionService;

    @Inject
    private CompetitionCategoryService competitionCategoryService;

    @Inject
    private RegisteredCoupleService registeredCoupleService;

    @Inject
    private DownloadService downloadService;

    public HttpEntity<byte[]> getCompetitionReport(HttpServletRequest request, Long competitionId) throws IOException {
        CompetitionDTO comp = competitionService.findOne(competitionId);
        if(null == comp) {
            return null;
        }
        String fileName = System.currentTimeMillis() + ".xlsx";
        String competitionName = comp.getName();
        List<CompetitionCategoryDTO> competitionCategories = competitionCategoryService.findByCompetitionId(
            competitionId);
        Workbook workBook = new XSSFWorkbook();
        Set<String> sheetNames = new HashSet<>();
        for(CompetitionCategoryDTO categoryDTO : competitionCategories) {
            generateCategorySheet(workBook, sheetNames, competitionName, categoryDTO);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workBook.write(os);
        return downloadService.download(fileName,
            new String[]{ "application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            os.toByteArray(),
            false);
    }

    private void generateCategorySheet(Workbook workBook,
                                       Set<String> sheetNames,
                                       String competitionName,
                                       CompetitionCategoryDTO categoryDTO) {
        Sheet sheet = workBook.createSheet(prepareSheetName(sheetNames, categoryDTO.getName()));
        SheetInfo sheetInfo = createSheetHeader(workBook, sheet, competitionName, categoryDTO.getName());
        List<RegisteredCoupleDTO> registeredCouples = registeredCoupleService.findByCategoryId(categoryDTO.getId());
        for(int i = 0; i < registeredCouples.size(); i++) {
            fillCoupleRow(sheetInfo, sheet, categoryDTO, registeredCouples.get(i), i + 1);
        }
        for(int i = 0; i < sheetInfo.cellPosition; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String prepareSheetName(Set<String> sheetNames, String categoryName) {
        String result = categoryName;
        if(categoryName.length() > SHEET_NAME_LIMITATION) {
            result = result.replace(" ", "");
            if(result.length() > SHEET_NAME_LIMITATION) {
                String limitedSheetName = result.substring(0, SHEET_NAME_LIMITATION);
                if(sheetNames.contains(limitedSheetName)) {
                    limitedSheetName = limitedSheetName.substring(0, SHEET_NAME_LIMITATION - 4);
                    for(int i = 1; i < 100; i++) {
                        String testString = limitedSheetName.concat("(" + i + ")");
                        if(!sheetNames.contains(testString)) {
                            result = testString;
                            break;
                        }
                    }
                } else {
                    result = limitedSheetName;
                }
            }
        }
        sheetNames.add(result);
        return result;
    }

    private SheetInfo createSheetHeader(Workbook workBook, Sheet sheet, String competitionName, String categoryName) {
        SheetInfo info = new SheetInfo();
        info.headerStyle = prepareHeaderStyle(workBook);
        createHeaderCell(createHeaderRow(sheet, info), info.cellPosition, info.headerStyle, competitionName);
        createHeaderCell(createHeaderRow(sheet, info), info.cellPosition, info.headerStyle, categoryName);
        Row headerRow = createHeaderRow(sheet, info);
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "№");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Партнер");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Партнерша");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Дата рождения партнера");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Дата рождения партнерши");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Класс");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Город");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Клуб");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Тренер 1");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Тренер 2");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, info.cellPosition - 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, info.cellPosition - 1));
        return info;
    }

    private CellStyle prepareHeaderStyle(Workbook workBook) {
        CellStyle style = workBook.createCellStyle();
        Font font = workBook.createFont();
        font.setFontHeight((short)300);
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private Row createHeaderRow(Sheet sheet, SheetInfo info) {
        return sheet.createRow(info.rowPosition++);
    }

    private Cell createHeaderCell(Row headerRow, int cellPosition, CellStyle headerStyle, String value) {
        Cell cell = headerRow.createCell(cellPosition);
        cell.setCellValue(value);
        cell.setCellStyle(headerStyle);
        return cell;
    }

    private void fillCoupleRow(SheetInfo sheetInfo,
                               Sheet sheet,
                               CompetitionCategoryDTO categoryDTO,
                               RegisteredCoupleDTO couple,
                               int coupleNumber) {
        Row row = sheet.createRow(sheetInfo.rowPosition++);
        sheetInfo.cellPosition = 0;
        row.createCell(sheetInfo.cellPosition++).setCellValue(coupleNumber);
        Cell cell = row.createCell(sheetInfo.cellPosition++);
        cell.setCellValue(couple.getPartner1Surname() + " " + couple.getPartner1Name());
        if(couple.isSoloCouple()) {
            row.createCell(sheetInfo.cellPosition++).setCellValue("-");
        } else {
            row.createCell(sheetInfo.cellPosition++)
                .setCellValue(couple.getPartner2Surname() + " " + couple.getPartner2Name());
        }
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getPartner1DateOfBirth().toString());
        if(couple.isSoloCouple()) {
            row.createCell(sheetInfo.cellPosition++).setCellValue("-");
        } else {
            row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getPartner2DateOfBirth().toString());
        }
        row.createCell(sheetInfo.cellPosition++).setCellValue(fillDanceCategoryString(categoryDTO, couple));
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getLocation());
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getOrganization());
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getTrainer1());
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getTrainer2());
    }

    private String fillDanceCategoryString(CompetitionCategoryDTO categoryDTO, RegisteredCoupleDTO couple) {
        DanceCategoryEnum danceCategory = null;
        if(null != categoryDTO.getDanceCategory()) {
            danceCategory = DanceCategoryEnum.valueOf(categoryDTO.getDanceCategory());
        }
        String value = "";
        if(null == danceCategory) {
            value = couple.getPartner1DanceClassST().getName() +
                    (!couple.isSoloCouple() ? "/" + couple.getPartner2DanceClassST().getName() : "");
            value = value.concat(" ");
            value = value.concat(couple.getPartner1DanceClassLA().getName() +
                                 (!couple.isSoloCouple() ? "/" + couple.getPartner2DanceClassLA().getName() : ""));
        } else {
            switch(danceCategory) {
                case LA:
                    value = couple.getPartner1DanceClassLA().getName() +
                            (!couple.isSoloCouple() ? "/" + couple.getPartner2DanceClassLA().getName() : "");
                    break;
                case ST:
                    value = couple.getPartner1DanceClassST().getName() +
                            (!couple.isSoloCouple() ? "/" + couple.getPartner2DanceClassST().getName() : "");
                    break;
            }
        }
        return value.trim();
    }

    private class SheetInfo {

        private CellStyle headerStyle;
        private int rowPosition;
        private int cellPosition;
    }

}
