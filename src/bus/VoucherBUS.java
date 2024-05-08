/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.VoucherDAL;
import dal.VoucherReleaseDAL;
import entity.Customer;
import entity.Voucher;
import entity.VoucherRelease;
import enums.ObjectType;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import util.GenerateQRCode;
import util.SendMail;

/**
 *
 * @author daoducdanh
 */
public class VoucherBUS {

    private VoucherDAL voucherDAL;

    public VoucherBUS() {
        voucherDAL = new VoucherDAL();
    }

    public Voucher getVoucherByCode(String code) {
        return voucherDAL.findByCode(code);
    }

    public Voucher getVoucherById(String id) {
        return voucherDAL.findById(id);
    }

    public List<Voucher> getVoucherByVoucherReleaseAndStatus(String voucherReleaseId, boolean status) {
        return voucherDAL.findByVoucherReleaseAndStatus(voucherReleaseId, status);
    }

    public List<Voucher> getVoucherByReleaseAndReleaseAtIsNull(String voucherReleaseId) {
        return voucherDAL.findByVoucherReleaseAndReleaseAtIsNull(voucherReleaseId);
    }

    public boolean insertVoucher(Voucher voucher) {
        return voucherDAL.insert(voucher);
    }

    public boolean deleteVoucherById(String id) {
        return voucherDAL.delete(id);
    }

    public boolean updateVoucher(Voucher voucher) {
        return voucherDAL.update(voucher);
    }

    public void VoucherRelease(List<Customer> customers, List<Voucher> vouchers, VoucherRelease voucherRelease, int soLuong) {
        DecimalFormat dcmf = new DecimalFormat("#,### đ");
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SendMail sendMail = new SendMail();
        String type = "";
        if (voucherRelease.getObjectType() == ObjectType.ALL) {
            type = "toàn bộ hóa đơn";
        } else if (voucherRelease.getObjectType() == ObjectType.PRODUCT) {
            type = "sản phẩm";
        } else {
            type = "vé xem phim";
        }
        List<File> files = new ArrayList<>();
        for (int i = 0; i < customers.size() * soLuong; i++) {
            Voucher vc = vouchers.get(i);
            String path = "pdf/" + vc.getVoucherId() + ".jpg";
            GenerateQRCode.generateQRCode(vc.getCode(), path);
            files.add(new File(path));
            vc.setReleaseAt(LocalDateTime.now());
            voucherDAL.update(vc);
        }
        ExecutorService executor = Executors.newFixedThreadPool(customers.size());
        for (int i = 0; i < customers.size(); i++) {
            Customer cs = customers.get(i);
            List<File> fileCus = new ArrayList<>();
            String body = "<div marginwidth=\"0\" marginheight=\"0\"\n"
                    + "    style=\"font-size:16px;line-height:24px!important;font-family:arial;background:#D2ECF5;padding:50px; color: #606060;\">\n"
                    + "    <div style=\"max-width: 600px; margin: auto; margin-left: auto;\">\n"
                    + "      <img src=\"https://i.pinimg.com/originals/5b/bf/f9/5bbff97d242dfdfebc01549278bb493d.jpg\" alt=\"\"\n"
                    + "        style=\"width: 100%; height: 300px; object-fit: cover;\">\n"
                    + "      <div style=\"background-color: #ffffff; padding: 20px; margin-top: -5px;\">\n"
                    + "        <p>" + cs.getName() + " thân mến!</p>\n"
                    + "        <p>Cảm ơn bạn đã ghé thăm và xem phim tại <Span style=\"color: #188aef; font-weight: 600;\">Galaxy Cinema </Span>của chúng tôi! "
                    + "Để cảm ơn bạn đã dành thời gian quý báu của mình để ủng hộ <Span style=\"color: #188aef; font-weight: 600;\">Galaxy Cinema.</Span> Chúng tôi thân gửi tặng bạn mã giảm giá "
                    + "<span style=\"font-weight: 600;\">" + dcmf.format(voucherRelease.getPrice()) + " </span>cho đơn có giá trị tối thiểu <span style=\"font-weight: 600;\">" + dcmf.format(voucherRelease.getMinPrice()) + " </span> "
                    + "và áp dụng trên <span style=\"font-weight: 600;\">"
                    + "" + type + "</span> trong lần ghé rạp kế tiếp.</p>\n"
                    + "        <h3>Mã giảm giá: </h3>\n"
                    + "        <div>\n";
            for (int j = i * soLuong; j < i * soLuong + soLuong; j++) {
                File file = files.get(j);
                fileCus.add(file);
            }
            for (int j = i * soLuong; j < i * soLuong + soLuong; j++) {
                Voucher vc = vouchers.get(i);
                body += "<p style=\"margin: 8px; text-align: center; color: #188aef;\">" + vc.getCode() + "</p>";
            }
            body += "</div>\n"
                    + "        <h3>*<span style=\"text-decoration: underline;\">Lưu ý</span>:</h3>\n"
                    + "        <ul>\n"
                    + "          <li style=\"margin-bottom: 5px;\">Thời gian hiệu lực mã giảm giá: <span style=\"font-weight: 600;\">" + fm.format(voucherRelease.getStartDate()) + "</span> - "
                    + "<span style=\"font-weight: 600;\">" + fm.format(voucherRelease.getEndDate()) + "</span></li>\n"
                    + "          <li style=\"margin-bottom: 5px;\">Mã chỉ áp dụng một lần trên một hóa đơn duy nhất</li>\n"
                    + "          <li style=\"margin-bottom: 5px;\">Vui lòng đưa mã này hoặc đưa mã QR cho nhân viên thanh toán để nhận được ưu đãi.</li>\n"
                    + "        </ul>\n"
                    + "        <p style=\"margin-top: 20px;\">Chúc bạn có một ngày tuyệt vời!</p>\n"
                    + "        <p>Thân trọng,</p>\n"
                    + "        <p>Galaxy Cinema</p>\n"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "  </div>";
            String body1 = body;
            executor.submit(() -> {
               sendMail.SendMailVoucher(cs.getEmail(), "Tri ân khách hàng", body1, fileCus); 
            });
            
        }

    }
}
