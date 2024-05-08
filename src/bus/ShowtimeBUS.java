/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.MovieDAL;
import dal.RoomDAL;
import dal.ShowtimeDAL;
import entity.Movie;
import entity.Room;
import entity.Showtime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class ShowtimeBUS {

    private ShowtimeDAL showtimeDAL;
    private MovieDAL movieDAL;

    public ShowtimeBUS() {
        showtimeDAL = new ShowtimeDAL();
        movieDAL = new MovieDAL();
    }

    public Map<String, List<String>> getAllShowtimeByMovieAndTime(String movieId, LocalDate date) {
        List<Showtime> showtimes = showtimeDAL.findByMovieAndTime(movieId, date);
        showtimes.sort((o1, o2) -> {
            if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return -1;
            }
            return 1;
        });
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (Showtime st : showtimes) {
            String roomId = st.getRoom().getRoomId();
            String showtimeId = st.getShowtimeId();
            List<String> list;
            if (map.containsKey(roomId)) {
                list = map.get(roomId);
                list.add(showtimeId);
                map.put(roomId, list);
            } else {
                list = new ArrayList<>();
                list.add(showtimeId);
                map.put(roomId, list);
            }
        }
        return map;
    }

    public boolean checkInsertShowtime(LocalDateTime startTime, LocalDateTime endTime,Room room) {
        List<Showtime> showtimes = showtimeDAL.findAllByRoomAndMovieAndTime(room.getRoomId(), startTime.toLocalDate());
        showtimes.sort((o1, o2) -> {
            if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return -1;
            }
            return 1;
        });
        if (showtimes.isEmpty()) { // TH1: Chưa có suất chiếu nào trong ngày
            return true;
        }
        if (endTime.isBefore(showtimes.get(0).getStartTime())) {// TH2: Thêm ở đầu
            return true;
        }
        Showtime showtimeEnd = showtimes.get(showtimes.size() - 1);

        if (showtimeEnd.getStartTime().plusMinutes(movieDAL.findById(showtimeEnd.getMovie().getMovieId()).getRunningTime())
                .isBefore(startTime)) { // TH3: Thêm ở cuối
            return true;
        }
        for (int i = 0; i < showtimes.size() - 1; i++) { // TH4: Thêm ở giữa
            Showtime showtime1 = showtimes.get(i);
            Showtime showtime2 = showtimes.get(i + 1);
            LocalDateTime start = showtime1.getStartTime().plusMinutes(movieDAL.findById(showtime1.getMovie().getMovieId()).getRunningTime());
            if (start.isBefore(startTime) && endTime.isBefore(showtime2.getStartTime())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean insertShowtime(Showtime showtime){
        return showtimeDAL.insert(showtime);
    }
    public List<Showtime> getAllShowtimeByRoomAndTimeAndKeyword(String roomId, LocalDate date, String keyword) {
        return showtimeDAL.findByRoomAndTimeAndKeyword(roomId, date, keyword);
    }

    public boolean checkShowtimeByPhong(String roomId) {
        return showtimeDAL.checkShowTimeByPhong(roomId);
    }

    public Showtime getShowtimeById(String id) {
        return showtimeDAL.findById(id);
    }
}
