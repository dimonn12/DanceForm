package by.danceform.app.domain;

import com.google.common.base.Objects;

import javax.persistence.MappedSuperclass;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by dimonn12 on 07.10.2016.
 */

@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements IEntity<ID> {

    private static final long serialVersionUID = 2586176618057178629L;

    public static final LocalDate DEFAULT_END_DATE = LocalDate.of(3000, 1, 1);

    public abstract void setId(ID id);

    public <T extends AbstractEntity<ID>, ID extends Serializable> T deepCopy() {
        T obj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(this);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            obj = (T) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.getId() == null || ((AbstractEntity) obj).getId() == null) {
            return false;
        }

        return Objects.equal(this.getId(), ((AbstractEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

}
