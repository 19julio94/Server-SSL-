
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author JP
 */
public class Servidor extends javax.swing.JFrame {

    /**
     * Creates new form Servidor
     */
    public Servidor() {
        initComponents();
    }
    
    //Creamos variables estaticas para fque sexa mais comodo 
    static SSLServerSocketFactory serverSocketFactory;
    static SSLServerSocket serverSocket;

    private static SSLSocket socket;
    static DataInputStream entrada;
    static DataOutputStream salida;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtarea = new javax.swing.JTextArea();
        txtfield = new javax.swing.JTextField();
        benviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtarea.setColumns(20);
        txtarea.setRows(5);
        jScrollPane1.setViewportView(txtarea);

        txtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfieldActionPerformed(evt);
            }
        });

        benviar.setText("ENVIAR");
        benviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                benviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(benviar, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfield)
                    .addComponent(benviar, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void benviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benviarActionPerformed
        //Boton enviar
        
        try {
            String msg = "";//creamos unha variable String chamada msg,na cal se gardan as mensaxes que se van a enviar

            msg = txtfield.getText().trim();//Recollo o que hai no textfield,formateo os posibles espazos en branco

            salida.writeUTF(msg);//Usei DataOutput Stream para a escritura,para asi poder escribir Strings dun tiron con writeUTF ,sen ter que recoller primeiro byte a byte
            salida.flush();//Limpio o fluxo de escritura
            txtarea.append("\n Servidor :" + txtfield.getText());//Envio o textarea a mensaxe que envio,co nome do que a envia

            txtfield.setText("");//Fago que o textfield volva a quedar limpo para enviar a proxima mensaxe

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_benviarActionPerformed

    private void txtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
        
        /*
        
    javax.net.ssl.keyStore para indicar o almacén donde esta o certificado que nos identifica.
    javax.net.ssl.keyStorePassword A clave para acceder o almacen e para acceder o certificado dentro de ese almacen,Este e o motivo polo cal as claves deben ser iguais
    javax.net.ssl.trustStore para indicar o almacén onde estan os certificados.
    javax.net.ssl.trustStorePassword A clave para acceder ao almacén e certificados dentro deste.
        
        */

        try {
            System.setProperty("javax.net.ssl.keyStore", "serverKey.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "servpass");
            System.setProperty("javax.net.ssl.trustStore", "serverTrustedCerts.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "clientpass");
            
            serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();//Creamos o socket SSl para o servidor
            

            serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket();
               //Indicamoslle o porto e a direccion
            InetSocketAddress direccion = new InetSocketAddress("localhost", 6000);

            System.out.println("Servidor escoitando peticions");
            
            serverSocket.bind(direccion);

            System.out.println("Servidor Aceptando Conexiones");
            //O Servidor porase a escoita de peticions
            socket = (SSLSocket) serverSocket.accept();

            System.out.println("Conexion establecida");

            String msgin = "";//Variable String que se encargara de recoller os mensaxes de entrada e gardalos nela
            //Fluxos de escritura e lectura
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            while (!msgin.equals("exit")) {//Bucle while para a lectura,mentres as mensaxes sexan distintas de EXIT:

                msgin = entrada.readUTF();//Leo os mensaxes con readUTF
                txtarea.setText(txtarea.getText().trim() + "\n Cliente:" + msgin);//Envio ao textArea a mensaxe recollida,co nome de quen a enviou e formateo os posibles espazos en branco

            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton benviar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea txtarea;
    private javax.swing.JTextField txtfield;
    // End of variables declaration//GEN-END:variables
}
