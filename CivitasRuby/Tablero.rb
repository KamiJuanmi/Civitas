# encoding: utf-8


module Civitas

  class Tablero
        
    attr_reader :numCasillaCarcel, :casillas

    def initialize(c_carcel)

      c_carcel>=1 ? @numCasillaCarcel=c_carcel : @numCasillaCarcel=1
      @casillas=Array.new
      @salida=Casilla.new('salida')
      @casillas << @salida      
      @porSalida=0
      @tieneJuez=false

    end

    def correcto
          
      return ((@casillas.length>@numCasillaCarcel)&&@tieneJuez)

    end

    def correcto2(numCasilla)

      return ((numCasilla<=@casillas.length) && correcto && (numCasilla>=0))

    end

    def getPorSalida
            
      ret=@porSalida

      if ret>0
        @porSalida-=1
      end
            
      return ret

    end

    def añadeCasilla(casilla)
            
      if @casillas.length==@numCasillaCarcel
        carcel=Casilla.new('Cárcel')
        @casillas << carcel
        @casillas << casilla
      else 
        @casillas << casilla
        if @casillas.length == @numCasillaCarcel
          carcel=Casilla.new('Cárcel')
          @casillas << carcel
        end    
      end  
        
    end

    def añadeJuez
        
      if !@tieneJuez
        juez=Casilla_Juez.new(@numCasillaCarcel)
        añadeCasilla(juez)
        @tieneJuez=true        
      end

    end

    def getCasilla(numCasilla)
      
      if correcto2(numCasilla)
        return @casillas[numCasilla]
      else
        return nil
      end
        
    end

    def nuevaPosicion(actual, tirada) 
        
      ret=-1

      if correcto 
                
        actual + tirada > @casillas.length-1 ? ret = ((actual + tirada) % @casillas.length) : ret = actual+tirada

        if ret != actual + tirada
          @porSalida+=1
        end

      end

      return ret

    end

    def calcularTirada(origen, destino)
        
      aux=destino- origen
      if(aux<0)
        aux=aux+@casillas.length
      end

      return aux
        
    end
      
  end

end
