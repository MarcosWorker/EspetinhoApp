package br.com.forum.espetinhoapp.model.bean;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by estagiario-manha on 16/10/17.
 */

public class Pedido extends RealmObject {

    @PrimaryKey
    private long id;
    @Required
    private RealmList<Espetinho> espetinhos;
    @Required
    private String dataPedido;
    @Required
    private String horaPedido;
    @Required
    private String dataEntrega;
    @Required
    private String horaEntrega;
    private String cliente;
    private int mesa;
    private double total;
    private int status;// 0 - em aberto ######  1 - entregue

    public Pedido() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<Espetinho> getEspetinhos() {
        return espetinhos;
    }

    public void setEspetinhos(RealmList<Espetinho> espetinhos) {
        this.espetinhos = espetinhos;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
