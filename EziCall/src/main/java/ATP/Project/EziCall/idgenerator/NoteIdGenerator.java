package ATP.Project.EziCall.idgenerator;

import ATP.Project.EziCall.models.Note;
import ATP.Project.EziCall.models.Ticket;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.stream.Stream;

public class NoteIdGenerator implements IdentifierGenerator {

    private String prefix = "NT";

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
//        String query = "SELECT id from Note";
//        Stream<String> count = session.createQuery(query, String.class).stream();
//
//        Long max = count.map(o -> o.replace(prefix, "")).mapToLong(Long::parseLong).max().orElse(0L);
//        return prefix +(String.format("%03d", max + 1));

        if (obj instanceof Note) {
            Note note = (Note) obj;
            Ticket ticket = note.getTicket();
            String ticketId = ticket.getTicketId();

            String sql = "SELECT COUNT(*) FROM note WHERE ticket_id = ?";
            Integer count = ((Number) session.createNativeQuery(sql)
                    .setParameter(1, ticketId)
                    .uniqueResult()).intValue();

            return ticketId + String.format("%03d", count + 1);
        }

        throw new HibernateException("Instance is not a Note.");
    }
}
