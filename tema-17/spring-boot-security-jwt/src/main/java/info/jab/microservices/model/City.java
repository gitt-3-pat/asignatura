package info.jab.microservices.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"municipio_codigo",
"densidad_por_km2",
"municipio_codigo_ine",
"nuts4_nombre",
"municipio_nombre",
"nuts4_codigo",
"superficie_km2"
})
public class City {

    @JsonProperty("municipio_codigo")
    private String municipioCodigo;
    @JsonProperty("densidad_por_km2")
    private Double densidadPorKm2;
    @JsonProperty("municipio_codigo_ine")
    private String municipioCodigoIne;
    @JsonProperty("nuts4_nombre")
    private String nuts4Nombre;
    @JsonProperty("municipio_nombre")
    private String municipioNombre;
    @JsonProperty("nuts4_codigo")
    private String nuts4Codigo;
    @JsonProperty("superficie_km2")
    private Double superficieKm2;
}