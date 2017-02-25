package by.danceform.app.service.reporting;

import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.service.competition.CompetitionService;
import by.danceform.app.service.couple.RegisteredCoupleService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    public Object getCompetitionReport(Long competitionId) {
        CompetitionDTO comp = competitionService.findOne(competitionId);
        if (null == comp) {
            return null;
        }
        String competitionName = comp.getName();
        List<CompetitionCategoryDTO> competitionCategories = competitionCategoryService.findByCompetitionId(competitionId);
        for (CompetitionCategoryDTO categoryDTO : competitionCategories) {
            String categoryName = categoryDTO.getName();
            List<RegisteredCoupleDTO> registeredCouples = registeredCoupleService.findByCategoryId(categoryDTO.getId());
            XSSFWorkbook workBook = new XSSFWorkbook();
        }
        return null;
    }
}
