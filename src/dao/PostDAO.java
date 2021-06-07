package dao;

import bean.Post;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DBHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class PostDAO {
    /**
     * 获取所有文章
     * @return 所有的 Posts
     * @throws Exception
     */
    public List<Post> getAllPosts() throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "select id, title, content, author, cover, likes, created from post order by created desc";
        try {
            return new QueryRunner().query(
                    conn, sql, new BeanListHandler<Post>(Post.class)
            );
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 根据 Post id
     * @param id Post 主键
     * @return 某篇 Post
     * @throws Exception
     */
    public Post getPostById(int id) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "select id, title, content, author, likes, created from post where id = ?";
        try {
            return new QueryRunner().query(
                    conn, sql, new BeanHandler<Post>(Post.class),id
            );
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 添加文章
     * @param post 要插入的文章
     * @return 带 id 的文章
     * @throws Exception
     */
    public Post addPost(Post post) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into post (title, content, author, cover) values (?,?,?,?)";
        Object[] parms = {
                post.getTitle(),post.getContent(),post.getAuthor(),post.getCover()
        };
        try {
            QueryRunner run = new QueryRunner();
            BigDecimal res = run.insert(conn, sql, new ScalarHandler<BigDecimal>(),parms);
            post.setId(res.longValue());
            return post;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 添加点赞数量
     * @param id 获取文章
     * @return 点赞数量
     * @throws Exception
     */
    public int like(int id) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "update post set likes = likes + 1 where id = ?";
        try {
            QueryRunner runner = new QueryRunner();
            runner.update(conn, sql, id);
            sql = "select likes from post where id = ?";
            return (int) runner.query(conn, sql, new ScalarHandler<>(),id);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 删除某篇 Post
     * @param id
     * @throws Exception
     */
    public void delete(String id) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "delete from post where id = ?";
        try {
            new QueryRunner().update(conn, sql, id );
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
