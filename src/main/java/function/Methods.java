package function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
    /*
	 Pattern.compile(textInput, Pattern.CASE_INSENSITIVE): khởi tạo đối tượng Pattern
	 	textInput -> tham số tìm kiếm
	 	Pattern.CASE_INSENSITIVE -> không phân biệt chữ hoa và thường

	 pattern.matcher(textKiemTra): khởi tạo đối tượng Matcher
	 	textKiemTra -> tham số chuỗi ban đầu

	 matcher.find(): trả về true nếu có ngược lại là false
	 */
    public static boolean timKiem(String textInput, String textKiemTra) {
        Pattern pattern = Pattern.compile(textInput.trim(), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(textKiemTra);
        return matcher.find();
    }
}
