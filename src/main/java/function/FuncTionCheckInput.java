/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import com.core.cafe_shop_maven.BUS.PhieuNhapBUS;

/**
 *
 * @author Asus
 */
// Trung -> created
public class FuncTionCheckInput {
    private static FuncTionCheckInput instance;
    
    public FuncTionCheckInput() {
    }
    
    public static FuncTionCheckInput getInstance() {
        if (instance == null)
            instance = new FuncTionCheckInput();
        return instance;
    }
    
    public boolean isNumeric(String input) {
        if (input == null) {
            return false;
        }
        try {
            Double.parseDouble(input);  // Thử chuyển chuỗi thành số (double)
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
