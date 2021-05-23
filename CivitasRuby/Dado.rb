require 'singleton'
module Civitas
class Dado
  include Singleton
  @@SalidaCarcel=5

  def tirar
    @debug ? @ultimoResultado=1 : @ultimoResultado=(rand 1..6)
    return ultimoResultado
  end

  def initialize
    @ultimoResultado=0
    @debug=false
  end

  def salgoDeLaCarcel
    tirar
    return ultimoResultado==5
  end

  def quienEmpieza(num)
    tope=num-1
    return rand 0..tope
  end

  def setDebug(d)
    @debug=d
    @debug ? modo='Activado' : modo='Desactivado'
    Diario.instance.ocurre_evento('El modo debug del dado cambia a: ' + modo + '.')
  end

  attr_reader:ultimoResultado

end

end

