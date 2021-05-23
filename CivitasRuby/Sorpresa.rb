module Civitas
  
class Sorpresa
  
  def initialize(texto)
    @texto = texto
  end

  def jugadorCorrecto(actual, todos)

    return actual>=0 && actual<todos.length

  end

  def informe(actual, todos)

    if jugadorCorrecto(actual, todos)
      
      Diario.instance.ocurre_evento("Se está aplicando una sorpresa al jugador " + todos[actual].nombre)
    
    end
    
  end

  private_class_method :new
  end
  
end