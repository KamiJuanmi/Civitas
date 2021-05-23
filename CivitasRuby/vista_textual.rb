#encoding:utf-8
require_relative 'OperacionesJuego'
require 'io/console'


module Civitas

  class Vista_textual

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getc
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    def salirCarcel
      lista_Salidas= [SalidasCarcel::PAGANDO,SalidasCarcel::TIRANDO]
      lista=["Pagando.","Tirando."]
      opcion=menu("Elige como quiere salir de la carcel:",lista)
      return lista_Salidas[opcion]

    end

    
    def comprar

      lista=["No.","Si."]
      opcion=menu("¿Quiere comprar esta calle?",lista)
      lista_Respuestas = [Respuestas::NO,Respuestas::SI]
      return lista_Respuestas[opcion]

    end

    def gestionar

      lista=["Vender","Hipotecar","Cancelar_Hipoteca","Construir_Casa","Construir_Hotel","Terminar"]
      opcion1=menu("Elija la gestion inmobiliaria de realizar:",lista)
      @iGestion=opcion1
      
      lista_Gestiones = [GestionesInmobiliarias::VENDER,GestionesInmobiliarias::HIPOTECAR,GestionesInmobiliarias::CANCELAR_HIPOTECA,GestionesInmobiliarias::CONSTRUIR_CASA,GestionesInmobiliarias::CONSTRUIR_HOTEL,GestionesInmobiliarias::TERMINAR]
      if opcion1 != 5 
            lista2=@juegoModel.getJugadorActual.getPropiedadesString
            opcion2=menu("¿Sobre que propiedad desea realizar la gestion?",lista2)
            @iPropiedad=opcion2
      end
      


    end

    def getGestion
      return @iGestion
    end

    def getPropiedad
      return @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts "La siguiente operacion que va a ser realizada es: " + operacion.to_s
    end

    def mostrarEventos
      while Diario.instance.eventos_pendientes
        puts Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
    end

    def actualizarVista
      puts "-------------------"
      puts @juegoModel.getJugadorActual.toString
      puts "Sus propiedades son:"
      puts @juegoModel.getJugadorActual.getPropiedadesString
      puts "-------------------"
      puts "La casilla actual es:"
      puts @juegoModel.getCasillaActual.toString
      puts "-------------------"

    end

  end

end
