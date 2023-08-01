package io.github.lucasalmeida.cafeconnection.dto;


public class ParticipanteDTO {
    private Long usuario_id;
    private Long opcao_id;
    private Long data_id;

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Long getOpcao_id() {
        return opcao_id;
    }

    public void setOpcao_id(Long opcao_id) {
        this.opcao_id = opcao_id;
    }

    public Long getData_id() {
        return data_id;
    }

    public void setData_id(Long data_id) {
        this.data_id = data_id;
    }

    public ParticipanteDTO(Long usuario_id, Long opcao_id, Long data_id) {
        this.usuario_id = usuario_id;
        this.opcao_id = opcao_id;
        this.data_id = data_id;
    }

    public ParticipanteDTO() {
    }
}

