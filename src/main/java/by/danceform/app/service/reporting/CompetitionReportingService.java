package by.danceform.app.service.reporting;

import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.service.competition.CompetitionService;
import by.danceform.app.service.couple.RegisteredCoupleService;
import by.danceform.app.web.rest.util.DownloadUtil;
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
import java.util.List;

/**
 * Created by USER on 22.02.2017.
 */
@Service
public class CompetitionReportingService {

    @Inject
    private CompetitionService competitionService;

    @Inject
    private CompetitionCategoryService competitionCategoryService;

    @Inject
    private RegisteredCoupleService registeredCoupleService;

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
        for(CompetitionCategoryDTO categoryDTO : competitionCategories) {
            generateCategorySheet(workBook, competitionName, categoryDTO);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workBook.write(os);
        return DownloadUtil.download(fileName,
            new String[]{ "application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            os.toByteArray());
    }

    private void generateCategorySheet(Workbook workBook, String competitionName, CompetitionCategoryDTO categoryDTO) {
        Sheet sheet = workBook.createSheet(categoryDTO.getName());
        SheetInfo sheetInfo = createSheetHeader(workBook, sheet, competitionName);
        List<RegisteredCoupleDTO> registeredCouples = registeredCoupleService.findByCategoryId(categoryDTO.getId());
        for(RegisteredCoupleDTO couple : registeredCouples) {
            fillCoupleRow(workBook, sheetInfo, sheet, categoryDTO, couple);
        }
        for(int i = 0; i < sheetInfo.cellPosition; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private SheetInfo createSheetHeader(Workbook workBook, Sheet sheet, String competitionName) {
        /*CellStyle style = workBook.createCellStyle();
        Font font = workBook.createFont();
        font.setBold(true);
        font.setFontHeight((short)14);
        style.setFont(font);*/
        SheetInfo info = new SheetInfo();
        Row competitionNameRow = sheet.createRow(info.rowPosition++);
       // competitionNameRow.setRowStyle(style);
        competitionNameRow.createCell(info.cellPosition).setCellValue(competitionName);
        Row headerRow = sheet.createRow(info.rowPosition++);
        headerRow.createCell(info.cellPosition++).setCellValue("Партнер");
        headerRow.createCell(info.cellPosition++).setCellValue("Партнерша");
        headerRow.createCell(info.cellPosition++).setCellValue("Дата рождения партнера");
        headerRow.createCell(info.cellPosition++).setCellValue("Дата рождения партнерши");
        headerRow.createCell(info.cellPosition++).setCellValue("Класс");
        headerRow.createCell(info.cellPosition++).setCellValue("Город");
        headerRow.createCell(info.cellPosition++).setCellValue("Клуб");
        headerRow.createCell(info.cellPosition++).setCellValue("Тренер 1");
        headerRow.createCell(info.cellPosition++).setCellValue("Тренер 2");
        //headerRow.setRowStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, info.cellPosition - 1));
        return info;
    }

    private void fillCoupleRow(Workbook workBook,
                               SheetInfo sheetInfo,
                               Sheet sheet,
                               CompetitionCategoryDTO categoryDTO,
                               RegisteredCoupleDTO couple) {
        Row row = sheet.createRow(sheetInfo.rowPosition++);
        sheetInfo.cellPosition = 0;
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
        return value;
    }

    private class SheetInfo {

        private int rowPosition;
        private int cellPosition;
    }

}
