package Hobe.Restaurant.Repository;

import Hobe.Restaurant.Domain.Admin;
import Hobe.Restaurant.Domain.Booking;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class JdbcAdminRepository implements AdminRepository{
    private final DataSource dataSource;

    public JdbcAdminRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Admin save(Admin admin) {
        String sql = "insert into admin(address,startTime,endTime,id,phoneNum,email) values(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, admin.getAddress());
            pstmt.setString(2, admin.getStartTime());
            pstmt.setString(3, admin.getEndTime());
            pstmt.setInt(4, admin.getId());
            pstmt.setString(5, admin.getPhoneNumber());
            pstmt.setString(6, admin.getEmail());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                
            } else {
                throw new SQLException("실패");
            }
            return admin;
        }catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Admin output() {
        String sql = "select * from admin";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
//            pstmt.setLong(1, id); 뭔지 모르겠음
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Admin admin = new Admin();
                admin.setAddress(rs.getString("address"));
                admin.setStartTime(rs.getString("startTime"));
                admin.setEndTime(rs.getString("endTime"));
                admin.setId(rs.getInt("id"));
                admin.setPhoneNumber(rs.getString("phoneNum"));
                admin.setEmail(rs.getString("email"));
                return admin;
            }
            else {
                return null;
            }


        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }


    @Override
    public void UpdateInfo(Admin admin) {
        String sql = "update admin set address = ?, startTime = ?, " +
                "endTime = ?, phoneNum = ?, email = ? " +
                "where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, admin.getAddress());
            pstmt.setString(2, admin.getStartTime());
            pstmt.setString(3, admin.getEndTime());
            pstmt.setString(4, admin.getPhoneNumber());
            pstmt.setString(5, admin.getEmail());
            pstmt.setInt(6, admin.getId());
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
