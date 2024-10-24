package controle;

import vendasOline.Pedido;
import java.sql.*;

public class PedidoControle {
    private Connection conn;

    public PedidoControle(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return this.conn;
    }
    public int contarPedidos() {
        String sql = "SELECT COUNT(*) AS TOTAL FROM PEDIDO";
        int total = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getInt("TOTAL");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar pedidos: " + e.getMessage());
        }
        return total;
    }

    public void inserirPedido(Pedido pedido) {
        String sql = "INSERT INTO PEDIDO(DATA, VALOR_TOTAL, CLIENTE_ID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, Date.valueOf(pedido.getData()));
            pstmt.setBigDecimal(2, pedido.getValorTotal());
            pstmt.setInt(3, pedido.getClienteId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setId(rs.getInt(1));
            }
            System.out.println("Pedido inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    public void atualizarPedido(Pedido pedido) {
        String sql = "UPDATE PEDIDO SET DATA = ?, VALOR_TOTAL = ?, CLIENTE_ID = ? WHERE PEDIDO_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(pedido.getData()));
            pstmt.setBigDecimal(2, pedido.getValorTotal());
            pstmt.setInt(3, pedido.getClienteId());
            pstmt.setInt(4, pedido.getId());
            pstmt.executeUpdate();
            System.out.println("Pedido atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
        }
    }

    public void excluirPedido(int pedidoId) {
        String sql = "DELETE FROM PEDIDO WHERE PEDIDO_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pedidoId);
            pstmt.executeUpdate();
            System.out.println("Pedido excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pedido: " + e.getMessage());
        }
    }

    public void listarPedidos() {
        String sql = "SELECT * FROM PEDIDO";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(new Pedido(
                    rs.getInt("PEDIDO_ID"),
                    rs.getDate("DATA").toLocalDate(),
                    rs.getBigDecimal("VALOR_TOTAL"),
                    rs.getInt("CLIENTE_ID")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    public Pedido listarPedidoPorId(int pedidoId) {
        String sql = "SELECT * FROM PEDIDO WHERE PEDIDO_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pedidoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Pedido(
                    rs.getInt("PEDIDO_ID"),
                    rs.getDate("DATA").toLocalDate(),
                    rs.getBigDecimal("VALOR_TOTAL"),
                    rs.getInt("CLIENTE_ID")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedido por ID: " + e.getMessage());
        }
        return null;
    }
}