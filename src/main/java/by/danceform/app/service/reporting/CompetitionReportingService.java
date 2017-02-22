package by.danceform.app.service.reporting;

import by.danceform.app.service.competition.CompetitionService;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Created by USER on 22.02.2017.
 */
@Service
public class CompetitionReportingService {

    @Inject
    private CompetitionService competitionService;

    public Object getCompetitionReport(Long competitionId) {
        return null;
    }
}
