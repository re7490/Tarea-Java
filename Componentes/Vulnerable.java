package Componentes;

public interface Vulnerable {
    /**
     * Calcula el multiplicador de daño basado en la relacion con el elemento.
     * 
     * Compara elemento de la magia recibida contra las 
     * resistencias, debilidades o inmunidades propias de la entidad.
     * 
     * @param elementoMagia El tipo de elemento de la magia
     * @return Un valor double que actúa como multiplicador:
     *         - 2.0: Súper efectivo (Debilidad).
     *         - 1.0: Daño normal.
     *         - 0.5: Resistente.
     *         - 0.0: Inmune.
     */
    double evaluarDebilidad(Elemento elementoMagia);
}