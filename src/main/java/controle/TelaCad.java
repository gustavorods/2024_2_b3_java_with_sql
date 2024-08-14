    package controle;

    import java.awt.*;
    import java.text.ParseException;
    import javax.swing.*;
    import javax.swing.text.MaskFormatter;

    import conexao.conexao;

    import javax.swing.table.DefaultTableModel;

    import java.sql.*;

    public class TelaCad extends JFrame  {
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
            
            con_cliente.executaSQL("select * from tbclientes order by cod");
            preencherTabela();
            

        }
        
        public void preencherTabela() {
            tblClientes.getColumnModel().getColumn(0).setPreferredWidth(4);
            tblClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblClientes.getColumnModel().getColumn(2).setPreferredWidth(11);
            tblClientes.getColumnModel().getColumn(3).setPreferredWidth(14);
            tblClientes.getColumnModel().getColumn(4).setPreferredWidth(100);

            DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
            modelo.setNumRows(0);

            try {
                con_cliente.resultset.beforeFirst();
                while (con_cliente.resultset.next()) {
                    modelo.addRow(new Object[] {
                        con_cliente.resultset.getString("cod"), // adicionando na coluna 1
                        con_cliente.resultset.getString("nome"), // adicionando na coluna 2
                        con_cliente.resultset.getString("dt_nasc"),// adicionando na coluna 3
                        con_cliente.resultset.getString("telefone"), // adicionando na coluna 4
                        con_cliente.resultset.getString("email")// adicionando na coluna 5
                    });
                }
            }catch(SQLException erro) {
                JOptionPane.showMessageDialog(null,"\n Erro ao listar dados da tabela!! :\n "+erro,"Mensagem doPrograma",JOptionPane.INFORMATION_MESSAGE);        }
        }

        
        public static void main(String[] args) {
            new TelaCad();
        }
    }
