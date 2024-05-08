/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Movie;
import entity.MovieType;
import enums.MovieStatus;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class MovieDAL implements BaseDAL<Movie> {

    private Connection con;

    public MovieDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try {
            String sql = "select * from Movie";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String movieId = rs.getString("MovieId");
                String name = rs.getString("Name");
                int runningTime = rs.getInt("RunningTime");
                String country = rs.getString("Country");
                String language = rs.getString("Language");
                String description = rs.getString("Description");
                int releaseYear = rs.getInt("ReleaseYear");
                String author = rs.getString("Author");
                String poster = rs.getString("Poster");
                MovieStatus movieStatus = MovieStatus.valueOf(rs.getString("MovieStatus"));
                MovieType movieType = new MovieType(rs.getString("MovieTypeId"));
                Movie movie = new Movie(movieId, name, runningTime, country, language,
                        description, releaseYear, author, poster, movieStatus, movieType);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> findByShowtime(String search, String type, LocalDate date) {
        List<Movie> movies = new ArrayList<>();
        try {
            String sql = "select * from Movie\n"
                    + "where MovieStatus != 'STOPPED' and MovieId in (\n"
                    + "	select m.MovieId from Movie m \n"
                    + "	join Showtime st on m.MovieId = st.MovieId\n"
                    + "	join MovieType mt on m.MovieTypeId = mt.MovieTypeId\n"
                    + "	where FORMAT(?, 'yyyy-MM-dd') =  FORMAT(st.StartTime, 'yyyy-MM-dd')\n"
                    + "	and m.Name like ? ";
            if (!type.equals("Tất cả")) {
                sql += " and mt.Name = ? ";
            }
            sql += ")";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(date));
            pst.setString(2, "%" + search + "%");
            if (!type.equals("Tất cả")) {
                pst.setString(3, type);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String movieId = rs.getString("MovieId");
                String name = rs.getString("Name");
                int runningTime = rs.getInt("RunningTime");
                String country = rs.getString("Country");
                String language = rs.getString("Language");
                String description = rs.getString("Description");
                int releaseYear = rs.getInt("ReleaseYear");
                String author = rs.getString("Author");
                String poster = rs.getString("Poster");
                MovieStatus movieStatus = MovieStatus.valueOf(rs.getString("MovieStatus"));
                MovieType movieType = new MovieType(rs.getString("MovieTypeId"));
                Movie movie = new Movie(movieId, name, runningTime, country, language,
                        description, releaseYear, author, poster, movieStatus, movieType);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> findByKeywordAndMovieStatus(String Keyword, MovieStatus movieStatus) {
        List<Movie> movies = new ArrayList<>();
        try {
            String sql = "select * from Movie m\n"
                    + " join MovieType mt on m.MovieTypeId = mt.MovieTypeId\n"
                    + " where MovieStatus = ? ";
            if (!Keyword.trim().equals("")) {
                sql += "and (m.Name like ? or Author like ? or Country like ? or mt.Name like ?)";
            }
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, movieStatus.toString());
            if (!Keyword.trim().equals("")) {
                pst.setString(2, "%" + Keyword + "%");
                pst.setString(3, "%" + Keyword + "%");
                pst.setString(4, "%" + Keyword + "%");
                pst.setString(5, "%" + Keyword + "%");
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String movieId = rs.getString("MovieId");
                String name = rs.getString("Name");
                int runningTime = rs.getInt("RunningTime");
                String country = rs.getString("Country");
                String language = rs.getString("Language");
                String description = rs.getString("Description");
                int releaseYear = rs.getInt("ReleaseYear");
                String author = rs.getString("Author");
                String poster = rs.getString("Poster");
                MovieType movieType = new MovieType(rs.getString("MovieTypeId"));
                Movie movie = new Movie(movieId, name, runningTime, country, language,
                        description, releaseYear, author, poster, movieStatus, movieType);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    public boolean existsByName(String name){
        try{
            String sql = "select * from Movie where Name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean findByExistsShowtime(String id) {
        try {
            String sql = "select count(*) from Movie m\n"
                    + "  join Showtime st on m.MovieId = st.MovieId\n"
                    + "  where st.StartTime > GETDATE() and m.MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Movie findById(String id) {
        try {
            String sql = "select * from Movie where MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String movieId = rs.getString("MovieId");
                String name = rs.getString("Name");
                int runningTime = rs.getInt("RunningTime");
                String country = rs.getString("Country");
                String language = rs.getString("Language");
                String description = rs.getString("Description");
                int releaseYear = rs.getInt("ReleaseYear");
                String author = rs.getString("Author");
                String poster = rs.getString("Poster");
                MovieStatus movieStatus = MovieStatus.valueOf(rs.getString("MovieStatus"));
                MovieType movieType = new MovieType(rs.getString("MovieTypeId"));
                Movie movie = new Movie(movieId, name, runningTime, country, language,
                        description, releaseYear, author, poster, movieStatus, movieType);
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Movie findByName(String name) {
        try {
            String sql = "select * from Movie where Name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String movieId = rs.getString("MovieId");
                int runningTime = rs.getInt("RunningTime");
                String country = rs.getString("Country");
                String language = rs.getString("Language");
                String description = rs.getString("Description");
                int releaseYear = rs.getInt("ReleaseYear");
                String author = rs.getString("Author");
                String poster = rs.getString("Poster");
                MovieStatus movieStatus = MovieStatus.valueOf(rs.getString("MovieStatus"));
                MovieType movieType = new MovieType(rs.getString("MovieTypeId"));
                Movie movie = new Movie(movieId, name, runningTime, country, language,
                        description, releaseYear, author, poster, movieStatus, movieType);
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean insert(Movie movie) {
        try {
            String sql = "insert into Movie values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, movie.getName());
            pst.setInt(3, movie.getRunningTime());
            pst.setString(4, movie.getCountry());
            pst.setString(5, movie.getLanguage());
            pst.setString(6, movie.getDescription());
            pst.setInt(7, movie.getReleaseYear());
            pst.setString(8, movie.getAuthor());
            pst.setString(9, movie.getPoster());
            pst.setString(10, movie.getMovieStatus().toString());
            pst.setString(11, movie.getMovieType().getMovieTypeId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Movie movie) {
        try {
            String sql = "update Movie set Name = ?, RunningTime = ?, Country = ?, Language = ?, "
                    + "Description = ?, ReleaseYear = ?, Author = ?, Poster = ?, MovieStatus = ?, MovieTypeId = ? "
                    + "where MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, movie.getName());
            pst.setInt(2, movie.getRunningTime());
            pst.setString(3, movie.getCountry());
            pst.setString(4, movie.getLanguage());
            pst.setString(5, movie.getDescription());
            pst.setInt(6, movie.getReleaseYear());
            pst.setString(7, movie.getAuthor());
            pst.setString(8, movie.getPoster());
            pst.setString(9, movie.getMovieStatus().toString());
            pst.setString(10, movie.getMovieType().getMovieTypeId());
            pst.setString(11, movie.getMovieId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "update Movie set MovieStatus = ? where MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "STOPPED");
            pst.setString(2, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String generateId() {
        String id = "";
        try {
            String sql = "select max(MovieId) from Movie";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxMovieId = rs.getString(1);
            if (maxMovieId == null) {
                return "PH001";
            } else {
                int num = Integer.parseInt(maxMovieId.substring(2)) + 1;
                id = String.format("PH%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
