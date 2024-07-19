package com.tbsd.crawler.generator;

import com.tbsd.crawler.data.CategoryManager;
import com.tbsd.crawler.model.Category;
import com.tbsd.crawler.util.DBUtils;

import java.sql.*;
import java.util.Map;

public class CategoryGenerator {
    public static int generate() {
        int count = 0;
        try {
            Connection conn = DBUtils.getConnection();
            for (Map.Entry<Integer, Category> entry : CategoryManager.getCategory().entrySet()) {
                if (entry.getValue().dbId() != null) {
                    PreparedStatement st = conn.prepareStatement(
                            "SELECT 1 FROM category WHERE category_id = ?;"
                    );
                    st.setInt(1, entry.getValue().dbId());
                    boolean exist = st.executeQuery().next();
                    st.close();
                    if (exist) continue;
                }
                PreparedStatement st = conn.prepareStatement(
                        "INSERT INTO category (name) VALUES (?);",
                        Statement.RETURN_GENERATED_KEYS
                );
                st.setString(1, entry.getValue().name());
                if (st.executeUpdate() > 0) {
                    try (ResultSet rs = st.getGeneratedKeys()) {
                        if (rs.next())
                            entry.getValue().setDbId(rs.getInt(1));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    count++;
                }
                st.close();
            }
            conn.close();
            CategoryManager.saveData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
