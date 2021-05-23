module Civitas

    class TituloPropiedad

        @@factorInteresesHipoteca=1.1

        attr_reader :hipotecado, :nombre, :numCasas,:numHoteles
        attr_reader  :precioCompra, :precioEdificar, :propietario

        def initialize(nom, ab, fr, hb, pc, pe )
            @nombre=nom
            @alquilerBase=ab
            @factorRevalorizacion=fr
            @hipotecaBase=hb
            @precioCompra=pc
            @precioEdificar=pe
            @numCasas=0
            @numHoteles=0
            @hipotecado=false
            @propietario=nil
        end

        def actualizaPropietarioPorConversion(jugador)

            @propietario=jugador

        end

        def cancelarHipoteca(jugador)

            ret =false

            if @hipotecado && esEsteElPropietario(jugador)
                jugador.paga(getImporteCancelarHipoteca)
                @hipotecado=false
                ret=true
            end

            return ret

        end

        def cantidadCasasHoteles()

            return @numCasas+@numHoteles

        end

        def comprar(jugador)

            ret=false
            if !tienePropietario
                @propietario=jugador
                @propietario.paga(@precioCompra)
                ret=true
            end

            return ret

        end

        def construirCasaProp(jugador) 

            construccion=false
            puts "PRUEBA 1"

            if esEsteElPropietario(jugador)
                puts "PRUEBA2"
                @propietario.paga(@precioEdificar)
                @numCasas+=1
                construccion=true
            end

            return construccion

        end

        def construirHotelProp(jugador)

            construccion=false

            if esEsteElPropietario(jugador)
                @propietario.paga(@precioEdificar)
                @numHoteles+=1
                construccion=true
            end

            return construccion

        end
        
        def derruirCasas(n, jugador)

            if esEsteElPropietario(jugador) && n<=@numCasas
                @numCasas-=n
                return true
            end

            return false

        end

        def esEsteElPropietario(jugador)

            return jugador==@propietario

        end

        def getPrecioAlquiler() 
            
            alq = 0
            
            if !@hipotecado && !propietarioEncarcelado 
                alq=@alquilerBase*(1+(@numCasas*0.5)+(@numHoteles*2.5))
            end

            return alq

        end

        def getPrecioVenta() 
            
            return @precioCompra+(@numCasas+5*@numHoteles)*@factorRevalorizacion*@precioEdificar

        end
        
        def getImporteCancelarHipoteca() 

            return (@hipotecaBase*(1+(@numCasas*0.5)+(@numHoteles*2.5)))*@@factorInteresesHipoteca

        end

        def getImporteHipoteca() 

            return @hipotecaBase

        end

        def hipotecar(jugador)

            if !@hipotecado && esEsteElPropietario(jugador)
                @propietario.recibe(getImporteHipoteca)
                @hipotecado=true
                return true
            end

            return false

        end

        def propietarioEncarcelado()

            return @propietario.isEncarcelado()

        end

        def tienePropietario()

            return propietario!=nil

        end

        def toString()

            str= "\n"+'Nombre: ' + @nombre + "\n" + 'Número de casas ' + @numCasas.to_s + "\n" + 'Número de hoteles: ' + @numHoteles.to_s + "\n" + 'Precio de compra: ' + @precioCompra.to_s + "\n" + 'Propietario: '

            if(tienePropietario)
                
                str+=@propietario.nombre + "\n"
            else
                
                str+='sin propietario' + "\n\n"

            end

            return str
        end

        def tramitarAlquiler(jugador)
            
            if @propietario!=nil && !esEsteElPropietario(jugador)
                jugador.pagaAlquiler(getPrecioAlquiler)
                @propietario.recibe(getPrecioAlquiler)
            end


        end

        def vender(jugador)
            
            venta=false
            
            if esEsteElPropietario(jugador)
                
                jugador.recibe(@precioCompra+(@numCasas+5*@numHoteles)*@precioEdificar*@factorRevalorizacion)
                @propietario=nil
                @numCasas=0
                @numHoteles=0
                venta=true
                
            end
            
            return venta            
        end


        #METODOS GET

        private :getImporteHipoteca, :getPrecioAlquiler, :getPrecioVenta

        #METODOS 

        private :esEsteElPropietario, :propietarioEncarcelado

    end 
end