package controle;

import java.awt.*;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import conexao.conexao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class TelaCad extends JFrame  {
    conexao con_cliente;
    
    JLabel rCodigo, rNome, rTel, rData, rEmail, rPesquisar;
    JTextField tcodigo, tnome, temail, tPesquisar;
    JFormattedTextField tel, data;
    MaskFormatter mTel, mData;
    JButton btnPrimeiro, btnAnterior, btnProximo, btnUltimo, btnLimpar, btnNovoRes, btnGravar, btnAlterar, btnExcluir, btnPesquisar, btnSair;
    
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
        
        
        // Botões de navegação entre itens da tabela
            // Mover para o primeiro 
            btnPrimeiro = new JButton("Primeiro");
            btnPrimeiro.setBounds(400, 50, 100, 25);
            btnPrimeiro.setBackground(Color.decode("#aad3fa"));
            tela.add(btnPrimeiro);
            btnPrimeiro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                    con_cliente.resultset.first();
                    mostrar_Dados();
                    }
                    catch(SQLException erro) {
                       JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro:"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);            
                    }
                }
            });
            
            // Mover para o anterior
            btnAnterior = new JButton("Anterior");
            btnAnterior.setBounds(400, 90, 100, 25);
            btnAnterior.setBackground(Color.decode("#aad3fa"));
            tela.add(btnAnterior);
            btnAnterior.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                    con_cliente.resultset.previous();
                    mostrar_Dados();
                    }
                    catch(SQLException erro) {
                       JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro:"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);            
                    }
                }
            });
            
            // Mover para o próximo
            btnProximo = new JButton("Próximo");
            btnProximo.setBounds(400, 130, 100, 25);
            btnProximo.setBackground(Color.decode("#aad3fa"));
            tela.add(btnProximo);
            btnProximo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                    con_cliente.resultset.next();
                    mostrar_Dados();
                    }
                    catch(SQLException erro) {
                       JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro:"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);            
                    }
                }
            });
            
            // Mover para o ultimo
            btnUltimo = new JButton("Último");
            btnUltimo.setBounds(400, 170, 100, 25);
            btnUltimo.setBackground(Color.decode("#aad3fa"));
            tela.add(btnUltimo);
            btnUltimo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                    con_cliente.resultset.last();
                    mostrar_Dados();
                    }
                    catch(SQLException erro) {
                       JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro:"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);            
                    }
                }
            });
        
        
        // Botões de interagir com dados 
            // Limpar
            btnLimpar = new JButton("Limpar");
            btnLimpar.setBounds(550,50,100,25);
            btnLimpar.setBackground(Color.decode("#aad3fa"));
            tela.add(btnLimpar);
            btnLimpar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tcodigo.setText("");
                    tnome.setText("");
                    temail.setText("");
                    data.setText("");
                    tel.setText("");
                }
            });
            
            // Gravar
            btnGravar = new JButton("Gravar");
            btnGravar.setBounds(550,90,100,25);
            btnGravar.setBackground(Color.decode("#aad3fa"));
            tela.add(btnGravar);
            btnGravar.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   String nome = tnome.getText();
                   String data_nasc = data.getText();
                   String telefone = tel.getText();
                   String email = temail.getText();

                   try {
                       String insert_sql="insert into tbclientes (nome,telefone, email, dt_nasc) values ('" + nome + "','" + telefone+ "','" + email + "','" + data_nasc + "')";
                       con_cliente.statement.executeUpdate(insert_sql);
                       JOptionPane.showMessageDialog(null, "Gravação realizada com sucesso!!", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);

                       con_cliente.executaSQL("select * from tbclientes order by cod");
                       preencherTabela();
                   }catch(SQLException errosql) {
                       JOptionPane.showMessageDialog(null, "\n Erro na gravação :\n" +errosql, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                   } 
               } 
            });
            
            // Alterar
            btnAlterar = new JButton("Alterar");
            btnAlterar.setBounds(550,130,100,25);
            btnAlterar.setBackground(Color.decode("#aad3fa"));
            tela.add(btnAlterar);
            btnAlterar.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   String nome = tnome.getText();
                   String data_nasc = data.getText();
                   String telefone = tel.getText();
                   String email = temail.getText();
                   String sql;
                   String msg="";

                   try {
                       if(tcodigo.getText().equals("")) {
                           sql="insert into tbclientes (nome,telefone, email, dt_nasc) values ('" + nome + "','" + telefone+ "','" + email + "','" + data_nasc + "')";
                           msg="Gravação de um novo registro";
                       } else {
                           sql="update tbclientes set nome='" + nome + "',telefone='" + telefone + "', email='" + email + "',dt_nasc='" + data_nasc + "' where cod = " + tcodigo.getText();
                           msg="Alteração de um registro";
                       }

                       con_cliente.statement.executeUpdate(sql);
                       JOptionPane.showMessageDialog(null, "Gravação realizada com sucesso!!", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);

                       con_cliente.executaSQL("select * from tbclientes order by cod");
                       preencherTabela();
                   }
                   catch(SQLException errosql) {
                       JOptionPane.showMessageDialog(null, "\n Erro na gravação :\n" +errosql, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                   } 
               } 
            });
            
            // Excluir
            btnExcluir = new JButton("Excluir");
            btnExcluir.setBounds(550,170,100,25);
            btnExcluir.setBackground(Color.decode("#aad3fa"));
            tela.add(btnExcluir);
            btnExcluir.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   String sql="";
                   try {
                       int resposta = JOptionPane.showConfirmDialog(rootPane, "Dseja excluir o registro: ", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, 3);
                       if(resposta==0) {
                          sql = "delete from tbclientes where cod = " + tcodigo.getText();
                          int excluir = con_cliente.statement.executeUpdate(sql);
                          if (excluir == 1) {
                              JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!!", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
                              con_cliente.executaSQL("select * from tbclientes order by cod");
                              con_cliente.resultset.first();
                              preencherTabela();
                              posicionarRegistro();
                          }
                          else {
                              JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário!!", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
                          }
                       }
                   } 
                   catch (SQLException exececao) {
                       JOptionPane.showMessageDialog(null, "Erro na exclusão: " + exececao, "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
                   }

               } 
            });
            
             // Pesquisar 
            rPesquisar = new JLabel("Pesquisar pelo nome do Cliente: ");
            rPesquisar.setBounds(50,480,200,30);
            tela.add(rPesquisar);

            tPesquisar = new JTextField();
            tPesquisar.setBounds(250, 485, 280,20);
            tela.add(tPesquisar);

            btnPesquisar = new JButton("Pesquisar");
            btnPesquisar.setBounds(550,480,100,25);
            btnPesquisar.setBackground(Color.decode("#aad3fa"));
            tela.add(btnPesquisar);
            btnPesquisar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        String pesquisar = "select * from tbclientes where nome like '" + tPesquisar.getText() + "%'";
                        con_cliente.executaSQL(pesquisar);

                        if(con_cliente.resultset.first()) {
                            preencherTabela();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "\n Não existe dados com este paramêtro!!", "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    catch(SQLException errosql) {
                        JOptionPane.showMessageDialog(null,"\n Os dados digitados não foram localizados!! :\n"+errosql,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            
            // Sair 
            btnSair = new JButton("Sair");
            btnSair.setBounds(660,480,100,25);
            btnSair.setBackground(Color.decode("#f55b5b"));
            tela.add(btnSair);
            btnSair.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        
        
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

        
        // Atalhos
        btnPrimeiro.setMnemonic('P');
        btnAnterior.setMnemonic('A');
        btnProximo.setMnemonic('x');
        btnUltimo.setMnemonic('U');
        btnLimpar.setMnemonic('L');
        btnGravar.setMnemonic('G');
        btnAlterar.setMnemonic('r');
        btnExcluir.setMnemonic('E');
        btnPesquisar.setMnemonic('S');
        btnSair.setMnemonic('I');
        
        

        
        
        // Configurações do JFrame
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        con_cliente.executaSQL("select * from tbclientes order by cod");
        preencherTabela();
        posicionarRegistro();

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
    
    public void posicionarRegistro() {
        try {
            con_cliente.resultset.first();
            mostrar_Dados();
        }
        catch(SQLException erro) {
           JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro:"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);            
        }
    }
    
    public void mostrar_Dados() {
        try {
            tcodigo.setText(con_cliente.resultset.getString("cod"));
            tnome.setText(con_cliente.resultset.getString("nome"));
            data.setText(con_cliente.resultset.getString("dt_nasc"));              
            tel.setText(con_cliente.resultset.getString("telefone"));
            temail.setText(con_cliente.resultset.getString("email"));

        }
        catch(SQLException erro) {
            JOptionPane.showMessageDialog(null,"Não localizou dados: "+erro,"Mensagem doPrograma",JOptionPane.INFORMATION_MESSAGE);            
        }
    }

    
    public static void main(String[] args) {
        new TelaCad();
    }
}
