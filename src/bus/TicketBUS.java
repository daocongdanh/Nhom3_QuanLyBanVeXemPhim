/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.TicketDAL;
import entity.Ticket;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class TicketBUS {
    private TicketDAL ticketDAL;
    public TicketBUS(){
        ticketDAL = new TicketDAL();
    }
    
    public List<Ticket> getByShowtime(String showtimeId){
        return ticketDAL.findByShowtime(showtimeId);
    }
}

