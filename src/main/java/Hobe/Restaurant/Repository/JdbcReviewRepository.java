package Hobe.Restaurant.Repository;

import Hobe.Restaurant.Domain.Review;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcReviewRepository implements ReviewRepository {
    private final DataSource dataSource;


    public JdbcReviewRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Review save(Review review){
        String sql = "insert into review(memberId, reviewText, memberName) values( ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, review.getMemberId());
            pstmt.setString(2 , review.getReviewText());
            pstmt.setString(3, review.getMemberName());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            return review;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Review> findAll(){
        String sql = "select * from review";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while(rs.next()) {
                Review review = new Review();
                review.setReviewText(rs.getString("reviewText"));
                review.setMemberId(rs.getLong("memberId"));
                review.setCreatedAt(rs.getTimestamp("createdAt"));
                review.setMemberName(rs.getString("memberName"));
                reviews.add(review);
            }
            return reviews;

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



