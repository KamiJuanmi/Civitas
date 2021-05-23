module Civitas

class MazoSorpresas

  def init
    @sorpresas =[]
    @cartasEspeciales=[]
    @barajadas=false
    @usadas=0

  end

  def initialize(debug=false)
    @debug=debug
    init
    if debug 
      Diario.ocurreEvento('El mazo de sorpresas se encuentra en modo debug.')
    end
  end

  def alMazo(s)
    if !@barajadas
      @sorpresas.push(s)
    end
  end

  def siguiente
    if (!@barajadas and @usadas==@sorpresas.length and !@debug)
      @sorpresas.shuffle
      @usadas=0
      @barajadas=true
    end
    @usadas+=1
    @ultimaSorpresa=@sorpresas[0]
    @sorpresas.push(@ultimaSorpresa)
    @sorpresas.shift
    return @ultimaSorpresa

  end

  def inhabilitarCartaEspecial(sorpresa)
    if @sorpresas.include? sorpresa
      @cartasEspeciales.push(sorpresa)
      @sorpresas.delete(sorpresa)
      Diario.instance.ocurre_evento('Se ha inhabilitado una sorpresa.')
    end

  end

  def habilitarCartaEspecial(sorpresa)
    if @cartasEspeciales.include? sorpresa
      @sorpresas.push(sorpresa)
      @cartasEspeciales.delete(sorpresa)
      Diario.instance.ocurre_evento('Se ha habilitado una sorpresa.')
    end

  end

  private :init

end

end
