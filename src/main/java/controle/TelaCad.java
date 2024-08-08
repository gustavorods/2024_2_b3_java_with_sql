package controle;

import java.awt.*;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import conexao.conexao;
import javax.swing.JOptionPane;

public class TelaCad extends JFrame {
    conexao con_cliente;
    
    JLabel rCodigo, rNome, rTel, rData, rEmail;
    JTextField tcodigo, tnome, temail;
    JFormattedTextField tel, data;
    MaskFormatter mTel, mData;
    
    JTable tblClientes;
    JScrollPane scp_tabela;
    
    public TelaCad() {
        con_cliente = new conexao();
        con_cliente.conecta();
        
        setTitle("Conexão java com MySql");
        Container tela = getContentPane();
        tela.setLayout(null); // Usando layout nulo
        
        // Código 
        rCodigo = new JLabel("Código: ");
        rCodigo.setBounds(50, 50, 50, 30);
        tela.add(rCodigo);

        tcodigo = new JTextField();
        tcodigo.setBounds(130, 50, 60, 25);
        tela.add(tcodigo);
        
        // Nome
        rNome = new JLabel("Nome: ");
        rNome.setBounds(50, 90, 50, 30);
        tela.add(rNome);

        tnome = new JTextField();
        tnome.setBounds(130, 90, 170, 25);
        tela.add(tnome);
       
        // Data
        rData = new JLabel("Data: ");
        rData.setBounds(50, 130, 50, 30);
        tela.add(rData);

        try {
            mData = new MaskFormatter("##/##/####");
            data = new JFormattedTextField(mData);
            data.setBounds(130, 130, 80, 25);
            tela.add(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Telefone
        rTel = new JLabel("Telefone: ");
        rTel.setBounds(50, 170, 80, 30);
        tela.add(rTel);

        try {
            mTel = new MaskFormatter("(##) ####-####");
            tel = new JFormattedTextField(mTel);
            tel.setBounds(130, 170, 120, 25);
            tela.add(tel);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        // Email 
        rEmail = new JLabel("Email: ");
        rEmail.setBounds(50, 210, 50, 30);
        tela.add(rEmail);
        
        temail = new JTextField();
        temail.setBounds(130, 210, 200, 25);
        tela.add(temail);
        
        // Configuração da JTable
        tblClientes = new JTable();
        scp_tabela = new JScrollPane(tblClientes);
        
        scp_tabela.setBounds(50, 250, 550, 200);
        tela.add(scp_tabela);
        
        tblClientes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tblClientes.setFont(new Font("Arial", Font.BOLD, 12));
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
            },
            new String[] {"Código", "Nome", "Data Nascimento", "Telefone", "Email"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        tblClientes.setAutoCreateRowSorter(true);

        // Configurações do JFrame
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new TelaCad();
    }
}
