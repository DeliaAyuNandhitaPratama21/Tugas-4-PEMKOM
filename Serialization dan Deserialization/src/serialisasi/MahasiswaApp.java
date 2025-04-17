/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serialisasi;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author ASUS
 */
public class MahasiswaApp extends JFrame {
    private JTextField txtNim, txtNama, txtJurusan;
    private JTextArea areaSerialization, areaDeserialization;
    private ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<>();
    private File file = new File("data_mahasiswa.ser");

    public MahasiswaApp() {
        setTitle("Aplikasi Data Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);

        // Panel input
        JPanel panelInput = new JPanel(new GridLayout(4, 2));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Data Mahasiswa"));

        panelInput.add(new JLabel("NIM"));
        txtNim = new JTextField();
        panelInput.add(txtNim);

        panelInput.add(new JLabel("Nama"));
        txtNama = new JTextField();
        panelInput.add(txtNama);

        panelInput.add(new JLabel("Jurusan"));
        txtJurusan = new JTextField();
        panelInput.add(txtJurusan);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(e -> tambahData());
        panelInput.add(btnTambah);

        JButton btnSimpan = new JButton("Simpan ke File");
        btnSimpan.addActionListener(e -> simpanData());
        panelInput.add(btnSimpan);

        areaSerialization = new JTextArea();
        areaDeserialization = new JTextArea();
        areaSerialization.setEditable(false);
        areaDeserialization.setEditable(false);

        JPanel panelSerialization = new JPanel(new BorderLayout());
        panelSerialization.setBorder(BorderFactory.createTitledBorder("Hasil Serialization"));
        panelSerialization.add(new JScrollPane(areaSerialization), BorderLayout.CENTER);

        JPanel panelDeserialization = new JPanel(new BorderLayout());
        panelDeserialization.setBorder(BorderFactory.createTitledBorder("Hasil Deserialization"));
        panelDeserialization.add(new JScrollPane(areaDeserialization), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelSerialization, panelDeserialization);
        splitPane.setDividerLocation(390);

        JPanel panelButton = new JPanel();
        JButton btnSerialize = new JButton("Lakukan Serialization");
        JButton btnDeserialize = new JButton("Lakukan Deserialization");

        btnSerialize.addActionListener(e -> tampilkanSerialization());
        btnDeserialize.addActionListener(e -> lakukanDeserialization());

        panelButton.add(btnSerialize);
        panelButton.add(btnDeserialize);

        add(panelInput, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);
    }

    private void tambahData() {
        String nim = txtNim.getText();
        String nama = txtNama.getText();
        String jurusan = txtJurusan.getText();

        if (!nim.isEmpty() && !nama.isEmpty() && !jurusan.isEmpty()) {
            daftarMahasiswa.add(new Mahasiswa(nim, nama, jurusan));
            JOptionPane.showMessageDialog(this, "Data ditambahkan");
            txtNim.setText("");
            txtNama.setText("");
            txtJurusan.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Lengkapi semua field!");
        }
    }

    private void simpanData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(daftarMahasiswa);
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke file");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data!");
        }
    }

    private void tampilkanSerialization() {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = fis.readAllBytes();
            String isi = new String(data); 
            areaSerialization.setText(isi);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal membaca data untuk serialization!");
        }
    }

    private void lakukanDeserialization() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Mahasiswa> data = (ArrayList<Mahasiswa>) ois.readObject();
            areaDeserialization.setText("");
            for (Mahasiswa mhs : data) {
                areaDeserialization.append(mhs.toString() + "\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal membaca data!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MahasiswaApp().setVisible(true);
        });
    }
}