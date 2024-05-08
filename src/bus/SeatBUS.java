/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.SeatDAL;
import entity.Seat;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class SeatBUS {
    private SeatDAL seatDAL;
    public SeatBUS(){
        seatDAL = new SeatDAL();
    }
    
    public List<Seat> getSeatByRoom(String roomId){
        return seatDAL.findByRoom(roomId);
    }
    public int getCountByRoom(String roomId){
        return seatDAL.countByRoom(roomId);
    }
    public boolean insertSeat(Seat seat){
        return seatDAL.insert(seat);
    }
    public boolean updateSeat(Seat seat){
        return seatDAL.update(seat);
    }
}
