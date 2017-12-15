package by.danceform.app.service.reporting;

import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.NamedReferenceDTO;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.service.competition.CompetitionService;
import by.danceform.app.service.couple.RegisteredCoupleService;
import by.danceform.app.web.rest.util.DownloadService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by USER on 22.02.2017.
 */
@Service
public class CompetitionReportingService {

    private static final int SHEET_NAME_LIMITATION = 31;

    private static final int COMPETITION_CATEGORY_NAME_LABEL_START_POSITION = 1;
    private static final int HEADER_LABEL_LENGTH = 1;
    private static final int COMPETITION_CATEGORY_NAME_LENGTH = 7;
    private static final int COMPETITION_NAME_LENGTH = 3;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
        String competitionName = comp.getName();
        String fileName = prepareFileName(competitionName);
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

    private String prepareFileName(String competitionName) {
        return TranslitUtils.toTranslit(competitionName).replace(" ", "") +
               "_" +
               LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
               "_" +
               System.currentTimeMillis() +
               ".xlsx";
    }

    private void generateCategorySheet(Workbook workBook,
                                       Set<String> sheetNames,
                                       String competitionName,
                                       CompetitionCategoryDTO categoryDTO) {
        Sheet sheet = workBook.createSheet(prepareSheetName(sheetNames,
            categoryDTO.getName() + " " + categoryDTO.getMaxDanceClass().getName()));
        SheetInfo sheetInfo = createSheetHeader(workBook,
            sheet,
            competitionName,
            categoryDTO.getName() + " - " + categoryDTO.getMaxDanceClass().getName());
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
        Row headerRow = createHeaderRow(sheet, info);
        info.cellPosition += COMPETITION_CATEGORY_NAME_LABEL_START_POSITION;
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Группа:");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, categoryName);
        info.cellPosition += COMPETITION_CATEGORY_NAME_LENGTH;
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, "Турнир:");
        createHeaderCell(headerRow, info.cellPosition++, info.headerStyle, competitionName);
        info.headerStyle = prepareHeaderInfoStyle(workBook);
        Row infoHeaderRow = createHeaderRow(sheet, info);
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "№");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Фамилия партнера");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Имя партнера");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Класс");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Г.р. (дд/мм/гггг)");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "№ книжки");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Фамилия партнерши");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Имя партнерши");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Класс");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Г.р. (дд/мм/гггг)");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "№ книжки");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Клуб");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Город");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Страна");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Ф. рук1");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "И. рук1");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Ф. рук2");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "И. рук2");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Ф. тренер1");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "И. тренер1");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Ф. тренер2");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "И. тренер2");
        createHeaderCell(infoHeaderRow, info.cellPosition++, info.headerStyle, "Отметка");
        sheet.addMergedRegion(new CellRangeAddress(0,
            0,
            COMPETITION_CATEGORY_NAME_LABEL_START_POSITION + HEADER_LABEL_LENGTH,
            COMPETITION_CATEGORY_NAME_LABEL_START_POSITION + HEADER_LABEL_LENGTH + COMPETITION_CATEGORY_NAME_LENGTH -
            HEADER_LABEL_LENGTH));
        sheet.addMergedRegion(new CellRangeAddress(0,
            0,
            COMPETITION_CATEGORY_NAME_LABEL_START_POSITION +
            HEADER_LABEL_LENGTH +
            COMPETITION_CATEGORY_NAME_LENGTH +
            HEADER_LABEL_LENGTH +
            HEADER_LABEL_LENGTH,
            COMPETITION_CATEGORY_NAME_LABEL_START_POSITION +
            HEADER_LABEL_LENGTH +
            COMPETITION_CATEGORY_NAME_LENGTH +
            HEADER_LABEL_LENGTH +
            COMPETITION_NAME_LENGTH));
        return info;
    }

    private CellStyle prepareHeaderStyle(Workbook workBook) {
        CellStyle style = workBook.createCellStyle();
        Font font = workBook.createFont();
        font.setFontHeight((short)260);
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private CellStyle prepareHeaderInfoStyle(Workbook workBook) {
        CellStyle style = workBook.createCellStyle();
        Font font = workBook.createFont();
        font.setFontHeight((short)200);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        return style;
    }

    private Row createHeaderRow(Sheet sheet, SheetInfo info) {
        info.cellPosition = 0;
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
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getPartner1Surname());
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getPartner1Name());
        row.createCell(sheetInfo.cellPosition++)
            .setCellValue(prepareDanceClass(categoryDTO,
                couple.getPartner1DanceClassLA(),
                couple.getPartner1DanceClassST(),
                couple.isHobbyCouple()));
        row.createCell(sheetInfo.cellPosition++)
            .setCellValue(couple.getPartner1DateOfBirth().format(DATE_TIME_FORMATTER));
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        if(couple.isSoloCouple()) {
            row.createCell(sheetInfo.cellPosition++).setCellValue("-");
            row.createCell(sheetInfo.cellPosition++).setCellValue("-");
            row.createCell(sheetInfo.cellPosition++).setCellValue("-");
            row.createCell(sheetInfo.cellPosition++).setCellValue("-");
        } else {
            row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getPartner2Surname());
            row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getPartner2Name());
            row.createCell(sheetInfo.cellPosition++)
                .setCellValue(prepareDanceClass(categoryDTO,
                    couple.getPartner2DanceClassLA(),
                    couple.getPartner2DanceClassST(),
                    couple.isHobbyCouple()));
            row.createCell(sheetInfo.cellPosition++)
                .setCellValue(couple.getPartner2DateOfBirth().format(DATE_TIME_FORMATTER));
        }
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getOrganization());
        row.createCell(sheetInfo.cellPosition++).setCellValue(couple.getLocation());
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
        fillTrainerName(row, sheetInfo, couple.getTrainer1());
        fillTrainerName(row, sheetInfo, couple.getTrainer2());
        row.createCell(sheetInfo.cellPosition++).setCellValue("");
    }

    private String prepareDanceClass(CompetitionCategoryDTO categoryDTO,
                                     NamedReferenceDTO danceClassLA,
                                     NamedReferenceDTO danceClassST,
                                     boolean isHobbyCouple) {
        String danceClass = "-";
        if(!isHobbyCouple) {
            DanceCategoryEnum danceCategory = null;
            if(null != categoryDTO.getDanceCategory()) {
                danceCategory = DanceCategoryEnum.valueOf(categoryDTO.getDanceCategory());
            }
            if(danceCategory == DanceCategoryEnum.LA) {
                danceClass = danceClassLA.getName();
            } else {
                if(danceCategory == DanceCategoryEnum.ST) {
                    danceClass = danceClassST.getName();
                } else {
                    danceClass = danceClassLA.getName() + "/" + danceClassST.getName();
                }
            }
        }
        return danceClass.trim();
    }

    private void fillTrainerName(Row row, SheetInfo sheetInfo, String trainerName) {
        if(!StringUtils.isBlank(trainerName)) {
            String[] trainer = trainerName.split(" ");
            row.createCell(sheetInfo.cellPosition++).setCellValue(trainer[0]);
            if(trainer.length > 1) {
                row.createCell(sheetInfo.cellPosition++).setCellValue(trainer[1]);
            } else {
                row.createCell(sheetInfo.cellPosition++).setCellValue("");
            }
        } else {
            row.createCell(sheetInfo.cellPosition++).setCellValue("");
            row.createCell(sheetInfo.cellPosition++).setCellValue("");
        }
    }

    private class SheetInfo {

        private CellStyle headerStyle;
        private int rowPosition;
        private int cellPosition;
    }
}
