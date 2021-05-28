package Hobe.Restaurant.Repository;

import Hobe.Restaurant.Domain.Booking;

import Hobe.Restaurant.Domain.Member;
import Hobe.Restaurant.Domain.Table;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
/*
    해결할 것
    예약변경에서 변경이전의 예약의 날짜도 검사해서 같은 날 같은 시각으로 변경이 안됨.
    삭제를 멤버아이디 기준으로 해서, 해당 회원의 모든 예약이 삭제됨.
 */

public class JdbcBookingRepository implements BookingRepository{
    private final DataSource dataSource;
    public JdbcBookingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Booking save(Long Memberid, Booking booking) {
        String sql = "insert into booking(date, startTime, endTime, tableNumber, howMany, memberId) values( ?, ?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, booking.getDate());
            pstmt.setString(2, booking.getStartTime());
            pstmt.setString(3, booking.getEndTime());
            pstmt.setInt(4, booking.getTableNumber());
            pstmt.setInt(5, booking.getHowMany());
            pstmt.setLong(6, Memberid);


            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                //member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return booking;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }
    @Override
    public Optional<Booking> findByMemberID(Long id) {
        String sql = "select * from booking where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Booking booking = new Booking();
                booking.setDate(rs.getString("date"));
                booking.setStartTime(rs.getString("startTime"));
                booking.setEndTime(rs.getString("endTime"));
                booking.setTableNumber(rs.getInt("tableNumber"));
                booking.setHowMany(rs.getInt("howMany"));
                return Optional.of(booking);
            }
            else {
                return Optional.empty();
            }


        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }


    }


    public List<Booking> findAll() {
        String sql = "select * from booking";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while(rs.next()) {
                Booking booking = new Booking();
                booking.setDate(rs.getString("date"));
                booking.setStartTime(rs.getString("startTime"));
                booking.setEndTime(rs.getString("endTime"));
                booking.setTableNumber(rs.getInt("tableNumber"));
                booking.setHowMany(rs.getInt("howMany"));
                bookings.add(booking);
            }

            return bookings;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Booking> findDate(String date) {
        String sql = "select * from booking where date = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            rs = pstmt.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while(rs.next()) {
                Booking booking = new Booking();
                booking.setDate(rs.getString("date"));
                booking.setStartTime(rs.getString("startTime"));
                booking.setEndTime(rs.getString("endTime"));
                booking.setTableNumber(rs.getInt("tableNumber"));
                booking.setHowMany(rs.getInt("howMany"));
                bookings.add(booking);
            }

            return bookings;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }
    public void UpdateBooking(Booking booking,long MemberID){
        String sql = "update booking set date = ?, startTime = ?, " +
                "endTime = ?, tableNumber = ?, howMany = ? " +
                "where memberId = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, booking.getDate());
            pstmt.setString(2, booking.getStartTime());
            pstmt.setString(3, booking.getEndTime());
            pstmt.setInt(4, booking.getTableNumber());
            pstmt.setInt(5, booking.getHowMany());
            pstmt.setLong(6, MemberID);
            rs = pstmt.executeQuery();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    public void DeleteBooking(long MemberID){
        String sql = "delete from booking where memberid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, MemberID);
            rs = pstmt.executeQuery();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
