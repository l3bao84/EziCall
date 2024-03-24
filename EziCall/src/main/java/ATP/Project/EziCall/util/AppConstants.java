package ATP.Project.EziCall.util;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AppConstants {

    public static final String TICKET_IS_NOT_EXIST = "Ticket này không tồn tại";

    public static final String TICKET_IS_CLOSED = "Ticket này đã đóng";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public static final DateTimeFormatter ORIGINAL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    public static final String CUS_IS_NOT_EXIST = "Khách hàng này không tồn tại";

    public static final String NOTE_IS_NOT_EXIST = "Note này không tồn tại";

    public static final String USERNAME_IS_ALREADY_EXIST = "Username đã có trong hệ thống";

    public static final String AGENT_IS_NOT_EXIST = "Nhân viên này không tồn tại";

    public static final String NO_DATA_LIST = "Không có dữ liệu";

}
