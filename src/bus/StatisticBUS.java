/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.BillDAL;
import dal.MovieDAL;
import dal.ProductBillDAL;
import dal.ProductDAL;
import dal.SeatDAL;
import dal.ShowtimeDAL;
import dal.TicketDAL;
import entity.Bill;
import entity.Movie;
import entity.Product;
import entity.Seat;
import entity.Showtime;
import entity.Ticket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author daoducdanh
 */
public class StatisticBUS {

    private ProductBillDAL productBillDAL;
    private MovieDAL movieDAL;
    private ShowtimeDAL showtimeDAL;
    private TicketDAL ticketDAL;
    private SeatDAL seatDAL;
    private ProductDAL productDAL;
    private BillDAL billDAL;

    public StatisticBUS() {
        productBillDAL = new ProductBillDAL();
        movieDAL = new MovieDAL();
        showtimeDAL = new ShowtimeDAL();
        ticketDAL = new TicketDAL();
        seatDAL = new SeatDAL();
        productDAL = new ProductDAL();
        billDAL = new BillDAL();
    }

    public Map<Product, Map<Integer, Double>> thongKeSanPhamTheoNam(int year) {
        Map<Product, Map<Integer, Double>> thongKe = new LinkedHashMap<>();
        Map<String, Integer> result = productBillDAL.getSoLuongNhieuNhatTheoNam(year);
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            Product product = productDAL.findById(entry.getKey());
            Integer soLuong = entry.getValue();
            Double tongTien = product.getSellPrice() * soLuong;
            Map<Integer, Double> map = new HashMap<>();
            map.put(soLuong, tongTien);
            thongKe.put(product, map);
        }
        return thongKe;
    }

    public Map<Product, Map<Integer, Double>> thongKeSanPhamTheoThang(int month, int year) {
        Map<Product, Map<Integer, Double>> thongKe = new LinkedHashMap<>();
        Map<String, Integer> result = productBillDAL.getSoLuongNhieuNhatTheoThang(month, year);
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            Product product = productDAL.findById(entry.getKey());
            Integer soLuong = entry.getValue();
            Double tongTien = product.getSellPrice() * soLuong;
            Map<Integer, Double> map = new HashMap<>();
            map.put(soLuong, tongTien);
            thongKe.put(product, map);
        }
        return thongKe;
    }

    public Map<Movie, Map<Integer, Double>> thongKePhimTheoNam(int year) {
        List<Movie> movies = movieDAL.findAll();
        Map<Movie, Map<Integer, Double>> thongKe = new LinkedHashMap<>();
        for (int i = 0; i < movies.size(); i++) {
            if (thongKe.size() == 5) {
                break;
            }
            Movie movie = movies.get(i);
            List<Showtime> listSt = showtimeDAL.findByMovie(movie.getMovieId());
            double tong = 0;
            int ve = 0;
            for (Showtime x : listSt) {
                List<Ticket> listTk = ticketDAL.getQuantityTicketByYearAndShowtime(year, x.getShowtimeId());
                for (Ticket y : listTk) {
                    Seat tmpSeat = seatDAL.findById(y.getSeat().getSeatId());
                    y.setShowtime(x);
                    y.setSeat(tmpSeat);
                    tong += y.calcTotal();
                }
                ve += listTk.size();
            }
            Map<Integer, Double> map = new HashMap<>();
            if (tong > 0) {
                map.put(ve, tong);
                thongKe.put(movie, map);
            }

        }
        return thongKe.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    Double value1 = (Double) o1.getValue().values().toArray()[0];
                    Double value2 = (Double) o2.getValue().values().toArray()[0];
                    if (value1 < value2) {
                        return 1;
                    }
                    return -1;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<Movie, Map<Integer, Double>> thongKePhimTheoThang(int month, int year) {
        List<Movie> movies = movieDAL.findAll();
        Map<Movie, Map<Integer, Double>> thongKe = new LinkedHashMap<>();
        for (int i = 0; i < movies.size(); i++) {
            if (thongKe.size() == 5) {
                break;
            }
            Movie movie = movies.get(i);
            List<Showtime> listSt = showtimeDAL.findByMovie(movie.getMovieId());
            double tong = 0;
            int ve = 0;
            for (Showtime x : listSt) {
                List<Ticket> listTk = ticketDAL.getQuantityTicketByMonthAndYearAndShowtime(month, year, x.getShowtimeId());
                for (Ticket y : listTk) {
                    Seat tmpSeat = seatDAL.findById(y.getSeat().getSeatId());
                    y.setShowtime(x);
                    y.setSeat(tmpSeat);
                    tong += y.calcTotal();
                }
                ve += listTk.size();
            }
            Map<Integer, Double> map = new HashMap<>();
            if (tong > 0) {
                map.put(ve, tong);
                thongKe.put(movie, map);
            }
        }
        return thongKe.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    Double value1 = (Double) o1.getValue().values().toArray()[0];
                    Double value2 = (Double) o2.getValue().values().toArray()[0];
                    if (value1 < value2) {
                        return 1;
                    }
                    return -1;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    
    public List<Map<Double,Double>> totalPercent(int month, int year){
        List<Map<Double,Double>> result = new ArrayList<>();
        Bill bill = new Bill();
        if(month == 0){
            double totalPrice = getTotalByYear(year);
            double totalTicket = 0;
            Map<Movie, Map<Integer, Double>> tickets = thongKePhimTheoNam(year);
            for(Map.Entry<Movie, Map<Integer, Double>> entry : tickets.entrySet()){
                Map<Integer, Double> map = entry.getValue();
                totalTicket += (double)map.values().toArray()[0];
            }
            double totalProduct = 0;
            Map<Product, Map<Integer, Double>> products = thongKeSanPhamTheoNam(year);
            for(Map.Entry<Product, Map<Integer, Double>> entry : products.entrySet()){
                Map<Integer, Double> map = entry.getValue();
                totalProduct += (double)map.values().toArray()[0];
            }
            double discount =  billDAL.getDiscountByMonthAndYear(month, year);
            double tax = totalPrice + discount - totalTicket - totalProduct;
            
            double ticketPercent = totalTicket / totalPrice * 100;
            double productPercent = totalProduct / totalPrice * 100;
            double taxPercent = tax / totalPrice * 100;
            double discountPercent = discount / totalPrice * 100;
            Map<Double, Double> map = new HashMap<>();
            map.put(totalTicket, ticketPercent);
            result.add(map);
            map = new HashMap<>();
            map.put(totalProduct, productPercent);
            result.add(map);
            map = new HashMap<>();
            map.put(tax, taxPercent);
            result.add(map);
            map = new HashMap<>();
            map.put(discount, discountPercent);
            result.add(map);
        }
        else{
            double totalPrice = getTotalByMonth(month,year);
            double totalTicket = 0;
            Map<Movie, Map<Integer, Double>> tickets = thongKePhimTheoThang(month,year);
            for(Map.Entry<Movie, Map<Integer, Double>> entry : tickets.entrySet()){
                Map<Integer, Double> map = entry.getValue();
                totalTicket += (double)map.values().toArray()[0];
            }
            double totalProduct = 0;
            Map<Product, Map<Integer, Double>> products = thongKeSanPhamTheoThang(month,year);
            for(Map.Entry<Product, Map<Integer, Double>> entry : products.entrySet()){
                Map<Integer, Double> map = entry.getValue();
                totalProduct += (double)map.values().toArray()[0];
            }
            double discount =  billDAL.getDiscountByMonthAndYear(month, year);
            double tax = totalPrice + discount - totalTicket - totalProduct;
            
            double ticketPercent = totalTicket / totalPrice * 100;
            double productPercent = totalProduct / totalPrice * 100;
            double taxPercent = tax / totalPrice * 100;
            double discountPercent = discount / totalPrice * 100;
            Map<Double, Double> map = new HashMap<>();
            map.put(totalTicket, ticketPercent);
            result.add(map);
            map = new HashMap<>();
            map.put(totalProduct, productPercent);
            result.add(map);
            map = new HashMap<>();
            map.put(tax, taxPercent);
            result.add(map);
            map = new HashMap<>();
            map.put(discount, discountPercent);
            result.add(map);
        }
        return result;
    }
    public Map<Integer, Double> getBillByMonthInYear(int year){
        return billDAL.getBillByMonthInYear(year);
    }
    public double getTotalByYear(int year){
        Map<Integer, Double> map = this.getBillByMonthInYear(year);
        double tong = 0;
        for(Map.Entry<Integer, Double> entry : map.entrySet()){
            tong += entry.getValue();
        }
        return tong;
    }
    public Map<String, Integer> getBillMaxMonthInYear(int year) {
        return billDAL.getBillMaxMonthInYear(year);
    }
    
    public int getQuantityBillInYear(int year) {
        return billDAL.getQuantityBillInYear(year);
    }
    
    public Map<Integer, Double> getBillByDayInMonth( int month, int year ){
        return billDAL.getBillByDayInMonth(month, year);
    }
    public double getTotalByMonth(int month, int year){
        Map<Integer, Double> map = this.getBillByDayInMonth(month,year);
        double tong = 0;
        for(Map.Entry<Integer, Double> entry : map.entrySet()){
            tong += entry.getValue();
        }
        return tong;
    }
    public int getQuantityBillInMonthAndYear(int month, int year) {
        return billDAL.getQuantityBillInMonthAndYear(month, year);
    }
    public Map<String, Integer> getBillMaxDayInMonth(int month, int year) {
        return billDAL.getBillMaxDayInMonth(month, year);
    }
}
