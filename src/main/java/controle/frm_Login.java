package controle;

import java.awt.*;
import java.text.ParseException;
import javax.swing.*;

import conexao.conexao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.sql.*;

public class frm_Login extends JFrame {
    conexao con_cliente;
    
    JPasswordField Pfsenha;
    JLabel LblUsuario, LblSenha, LblTitulo; 
    JTextField TxfUsuario, TxfSenha;
    JButton BtnLogar;
    
    public frm_Login() {
        con_cliente = new conexao();
        con_cliente.conecta();
        
        // Configurações do JFrame 
        setTitle("Conexão java com MySql");
        Container tela = getContentPane();
        tela.setBackground(Color.decode("#dfede3"));
        tela.setLayout(null); // Usando layout nulo
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        
        
        // Titulo 
        LblTitulo = new JLabel("Acesso ao Sistema");
        LblTitulo.setBounds(120,50,300,20);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        tela.add(LblTitulo);

        
        // Campo de usuario 
        LblUsuario = new JLabel("Usuário: ");
        LblUsuario.setBounds(50,150,100,20);
        LblUsuario.setFont(new Font("Arial", Font.BOLD, 13));
        tela.add(LblUsuario);
        
        TxfUsuario = new JTextField();
        TxfUsuario.setBounds(110,150,200,20);
        tela.add(TxfUsuario);

        
        // Campo de senha
        LblSenha = new JLabel("Senha: ");
        LblSenha.setBounds(50,190,100,20);
        LblSenha.setFont(new Font("Arial", Font.BOLD, 13));
        tela.add(LblSenha);
        
        TxfSenha = new JPasswordField();
        TxfSenha.setBounds(110,190,200,20);
        tela.add(TxfSenha);

        
        // Botão de enviar
        BtnLogar = new JButton("Entrar");
        BtnLogar.setBounds(170,250,130,20);
        tela.add(BtnLogar);
        BtnLogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    String pesquisa = "select * from tblusuario where usuario like '" + TxfUsuario.getText() + "' && senha = " +TxfSenha.getText() + "";
                    con_cliente.executaSQL(pesquisa);
                    
                    if(con_cliente.resultset.first()) {
                        JOptionPane.showMessageDialog(null, "Bem-Vindo " + con_cliente.resultset.getString("usuario"));
                        TelaCad mostra = new TelaCad();
                        mostra.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "\n Úsuário não encontrado!", "Mensagem do programa: ", JOptionPane.INFORMATION_MESSAGE);
                        con_cliente.desconectar();
                        System.exit(0);
                    }
                } catch(SQLException errosSQL) {
                    JOptionPane.showMessageDialog(null,"\n Os dados digitados não foram localizados!! :\n "+errosSQL,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);                }
            }
        });
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new frm_Login();
    }
}
