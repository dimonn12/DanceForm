package by.danceform.app.repository.document;

import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.repository.AbstractRepositoryTest;
import org.junit.Before;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Dmitry_Shanko on 1/3/2017.
 */
public class UploadedDocumentRepositoryTest
    extends AbstractRepositoryTest<UploadedDocumentRepository, UploadedDocument, Long> {

    private static final String EXISTING_FULL_NAME = "Full Name";
    private static final String EXISTING_PATH = "Path";
    private static final String EXISTING_EXTERNAL_PATH = "External path";
    private static final String EXISTING_CONTENT_CONTENT_TYPE = "Content type";
    private static final String EXISTING_UPLOADED_BY = "Uploaded by";

    private static final ZonedDateTime EXISTING_UPLOADED_DATE = ZonedDateTime.of(2017,
        2,
        16,
        12,
        13,
        14,
        0,
        ZoneId.systemDefault());

    private final UploadedDocument existing = new UploadedDocument();

    @Before
    public void init() {
        existing.setId(EXISTING_ID);
        existing.setFullName(EXISTING_FULL_NAME);
        existing.setPath(EXISTING_PATH);
        existing.setExternalPath(EXISTING_EXTERNAL_PATH);
        existing.setContentContentType(EXISTING_CONTENT_CONTENT_TYPE);
        existing.setUploadedBy(EXISTING_UPLOADED_BY);
        existing.setUploadedDate(EXISTING_UPLOADED_DATE);
    }

    @Override
    protected Long getExistingId() {
        return EXISTING_ID;
    }

    @Override
    protected UploadedDocument getExistingEntity() {
        return existing;
    }

    @Override
    protected UploadedDocument getNewEntity() {
        final UploadedDocument newEntity = new UploadedDocument();
        newEntity.setFullName(randomString(5));
        newEntity.setPath(randomString(6));
        newEntity.setExternalPath(randomString(7));
        newEntity.setContentContentType(randomString(8));
        newEntity.setUploadedBy(randomString(9));
        newEntity.setUploadedDate(ZonedDateTime.now());
        return newEntity;
    }
}
