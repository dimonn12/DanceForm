package by.danceform.app.service.util;

import by.danceform.app.converter.document.UploadedDocumentConverter;
import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.dto.document.AttachedDocumentDTO;
import by.danceform.app.security.SecurityUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

/**
 * Created by dimonn12 on 23.10.2016.
 */
@Component
public class UploadUtil {

    @Inject
    private UploadedDocumentConverter uploadedDocumentConverter;

    public UploadedDocument uploadFile(HttpServletRequest request, AttachedDocumentDTO attachedDocumentDTO) {
        UploadedDocument uploadedDocument = uploadedDocumentConverter.convertToEntity(attachedDocumentDTO);
        uploadedDocument.setUploadedDate(ZonedDateTime.now());
        uploadedDocument.setUploadedBy(SecurityUtils.getCurrentUserLogin());
        uploadedDocument.setPath("empty");
        uploadedDocument.setExternalPath("");
/*        String[] splittedName = StringUtils.split(attachedDocumentDTO.getFullName(), "\\.");
        if(splittedName.length > 1) {
            randomName = randomName.concat(".");
            randomName = randomName.concat(splittedName[1]);
        }*/
        uploadedDocument.setContentContentType(attachedDocumentDTO.getContentContentType());
        uploadedDocument.setFullName(attachedDocumentDTO.getFullName());
        uploadedDocument.setContent(attachedDocumentDTO.getContent());
        /*File f = new File(request.getServletContext().getRealPath("/") + "/" + externalPath);
        try {
            Files.write(f.toPath(), content);
            entity.setPath(f.getPath());
            entity.setExternalPath(externalPath);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }*/
        /*try {
            MinioClient s3Client = new MinioClient("https://s3.amazonaws.com", "YOUR-ACCESSKEYID", "YOUR-SECRETACCESSKEY");
        } catch(InvalidEndpointException e) {
            throw new RuntimeException(e);
        } catch(InvalidPortException e) {
            throw new RuntimeException(e);
        }
        */
        /*uploadedDocument = uploadedDocumentRepository.save(uploadedDocument);
        Competition comp = competitionRepository.findOne(attachedDocumentDTO.getEntityId());
        comp.setDetailsDocumentId(uploadedDocument.getId());
        competitionRepository.save(comp);
        UploadedDocumentDTO result = uploadedDocumentConverter.convertToDto(uploadedDocument);
        String fileName = request.getServletContext().getRealPath("files") + "\\" + fullName;

        File f = new File(fileName);
        try {
            f.createNewFile();
            Files.write(f.toPath(), attachedDocumentDTO.getContent());
            *//*FileOutputStream out = new FileOutputStream(f);
            out.write(attachedDocumentDTO.getContent());
            out.close();*//*
        } catch(IOException e) {
            e.printStackTrace();
        }
        return result;*/


        return uploadedDocument;
    }
}
