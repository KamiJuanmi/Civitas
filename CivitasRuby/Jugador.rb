module Civitas
    include Comparable
    class Jugador
        @@CasasMax=4
        @@CasasPorHotel=4
        @@HotelesMax=4
        @@PasoPorSalida=1000
        @@PrecioLibertad=200    
        @@SaldoInicial=7500
        
        attr_reader :CasasMax, :CasasPorHotel, :HotelesMax, :nombre, :numCasillaActual, :PrecioLibertad, :encarcelado
        attr_reader :PremioPasoSalida, :propiedades, :puedeComprar, :saldo, :PasoPorSalida
        attr_accessor :saldo, :salvoconducto, :puedeComprar, :encarcelado, :propiedades, :numCasillaActual
        
        def initialize(n)
            @nombre=n
            @numCasillaActual=0
            @encarcelado=false
            @puedeComprar=true
            @saldo=@@SaldoInicial
            @propiedades=[]
            @salvoconducto=nil
        end

        def copia(otro)

            @nombre=otro.nombre

            @saldo = otro.saldo
            @numCasillaActual = otro.numCasillaActual
            @salvoconducto = otro.salvoconducto
            @puedeComprar = otro.puedeComprar
            @encarcelado = otro.encarcelado
            @propiedades = otro.propiedades

        end

        def debeSerEncarcelado 
            @encarcelado ? ret=false : ret= true 

            if !@encarcelado&&!tieneSalvoConducto
                ret=true
            else 
                if tieneSalvoConducto
                    perderSalvoConducto
                    Diario.instance.ocurre_evento('El jugador ' + @nombre + ' se libra de la carcel.')
                    ret=false
                end
            end

            return ret
        end

        def encarcelar(numCasillaCarcel)
            
            if debeSerEncarcelado
                moverACasilla(numCasillaCarcel)
                @encarcelado=true
                Diario.instance.ocurre_evento('El jugador ' + @nombre + ' ha sido encarcelado.')
            end

            return @encarcelado
        end

        def obtenerSalvoconducto(s)
            
            ret=false

            if !encarcelado
                ret=true
                @salvoconducto=s
            end
            
            return ret
        end

        def perderSalvoConducto

            @salvoconducto.usada()

        end

        def tieneSalvoConducto

            return @salvoconducto!=nil

        end

        def puedeComprarCasilla

            @encarcelado ? @puedeComprar=false : @puedeComprar=true
            return @puedeComprar

        end

        def paga(cantidad)
        
            return modificarSaldo(-1*cantidad)
            
        end

        def pagaImpuesto(cantidad)
        
            return @encarcelado ? true : paga(cantidad)
        
        end

        def pagaAlquiler(cantidad)

            return pagaImpuesto(cantidad)

        end

        def recibe(cantidad)
        
            return encarcelado ? false : modificarSaldo(cantidad)
        
        end
        
        def modificarSaldo(cantidad)
        
            @saldo+=cantidad

            Diario.instance.ocurre_evento('Se ha modificado el saldo del jugador ' + @nombre + ' en: ' + cantidad.to_s + ' unidades.')
            
            return true
        
        end

        def moverACasilla(numCasilla)
        
            ret=false

            if !@encarcelado
                @numCasillaActual=numCasilla
                @puedeComprar=false
                ret=true
                Diario.instance.ocurre_evento('El jugador ' + @nombre + ' se mueve hasta la casilla ' + numCasilla.to_s)
            end

            return ret
        end


        def cancelarHipoteca(ip)

            ret=false

            if !@encarcelado
                
                if existeLaPropiedad(ip)

                    propiedad=@propiedades[ip]
                    importe=propiedad.getImporteCancelarHipoteca
                    
                    if puedoGastar(importe)
                        res=propiedad.cancelarHipoteca(self)

                        if res  
                            Diario.instance.ocurre_evento("El jugador "+ @nombre + " cancela la hipoteca de la propiedad " + ip.to_s)
                        end
                    end
                end
            end

            return ret 
        end

        def cantidadCasasHoteles
            
            veces=@propiedades.length
            i=0
            numCasasHoteles=0
            veces.times do
                numCasasHoteles+=@propiedades[i].numCasas
                numCasasHoteles+=@propiedades[i].numHoteles
                i+=1
            end
            
            return numCasasHoteles

        end

        def compareTo(otro)

            return self<=>otro  #NOS DEVUELVE -1 SI SELF ES MAYOR QUE EL OTRO
                                #NOS DEVUELVE 0 SI SON IGUALES
                                #NOS DEVUELVE 1 SI OTRO ES MAYOR QUE SELF

        end
        
        def <=> (other)

            @saldo<=>other.saldo

        end
        
        def comprar(titulo)

            ret=false

            if !@encarcelado

                if @puedeComprar

                    precio=titulo.precioCompra

                    if puedoGastar(precio)
                    
                        ret=titulo.comprar(self)

                        if ret
                            @propiedades<<titulo
                            Diario.instance.ocurre_evento("El jugador "+ @nombre + " compra la propiedad " + titulo.toString)
                        end
                        
                        @puedeComprar=false
                    end
                end
            end

            return ret
        end
        
        def construirCasaJug(ip) 

            res=false
            edificar=false

            if !@encarcelado 
                existe=existeLaPropiedad(ip)
                if existe
                    propiedad=@propiedades[ip]
                    edificar=puedoEdificarCasa(propiedad)
                    if edificar
                        res=propiedad.construirCasaProp(self)
                        
                        if res
                            Diario.instance.ocurre_evento("El jugador "+ @nombre + "construye casa en la propiedad"+ip.to_s)
                        end
                    end
                end
            end

            return res
        end
        
        def construirHotelJug(ip)

            ret=false
            edificar=false

            if !@encarcelado
                
                if existeLaPropiedad(ip)

                    propiedad=@propiedades[ip]
                    edificar=puedoEdificarHotel(propiedad)

                    if edificar

                        ret=propiedad.construirHotelProp(self)
                        casasPorHotel=@@CasasPorHotel
                        propiedad.derruirCasas(casasPorHotel, self)
                        Diario.instance.ocurre_evento("El jugador "+@nombre+" construye hotel en la propiedad "+ip.to_s)
                    end
                end
            end

            return ret
        end

        def getPropiedadesString
            prop=[]

            for i in (0..propiedades.size-1)
                prop<<propiedades[i].nombre
            end

            return prop
        end 
        
        def enBancarrota

            return @saldo <= 0

        end
    
        def existeLaPropiedad(ip)
            
            return ip >= 0 && ip < propiedades.length

        end

        def hipotecar(ip)

            res=false

            if !@encarcelado

                if existeLaPropiedad(ip)
                    propiedad=@propiedades[ip]
                    res=propiedad.hipotecar(self)
                end

                if res
                    Diario.instance.ocurre_evento("El jugador "+@nombre+" hipoteca la propiedad "+ip.to_s)
                end
            end
        end

        def isEncarcelado
        
            return @encarcelado

        end

        def pasaPorSalida
            
            modificarSaldo(@@PasoPorSalida)
            Diario.instance.ocurre_evento('El jugador ' + @nombre + ' pasa por la salida y se le incrementa el saldo en: ' + @@PasoPorSalida.to_s + ' unidades.')

        end
        
        def puedeSalirCarcelPagando

            return @saldo>=@@PrecioLibertad

        end

        def puedoEdificarCasa(propiedad)
            
            precio=propiedad.precioEdificar
            puedoEdificarCasa=false

            if puedoGastar(precio) && propiedad.numCasas < @@CasasMax
                puedoEdificarCasa=true;
            end

            return puedoEdificarCasa
        end

        def puedoEdificarHotel(propiedad) 

            puedoEdificarHotel=false
            precio=propiedad.precioEdificar

            if puedoGastar(precio)
            
                if (propiedad.numCasas >= @@CasasPorHotel) && (propiedad.numHoteles <  @@HotelesMax)
                    puedoEdificarHotel=true
                end
            end

            return puedoEdificarHotel
        end

        def puedoGastar(precio)
            
            ret=false

            if !@encarcelado 
                ret= @saldo>=precio
            end

            return ret
        end

        def salirCarcelPagando

            ret=false

            if @encarcelado && puedeSalirCarcelPagando
                paga(@@PrecioLibertad)
                @encarcelado=false
                Diario.instance.ocurre_evento('El jugador ' + @nombre + ' paga para salir de la carcel.')
                ret=true
            end

            return ret
        end

        def salirCarcelTirando
            
            ret=Dado.instance.salgoDeLaCarcel  

            if ret
                @encarcelado=false
                Diario.instance.ocurre_evento('El jugador ' + @nombre + ' consigue salir tirando de la carcel.')
            end

            return ret

        end
        
        def tieneAlgoQueGestionar

            return @propiedades.length!=0

        end

        def toString

            salida= 'Nombre: ' + @nombre + "\n"
            salida+= 'Saldo: ' + @saldo.to_s + "\n"
            salida+= 'Casilla actual: ' + @numCasillaActual.to_s + "\n"
            salida+= 'Encarcelado: ' + isEncarcelado.to_s + "\n\n"

            return salida
        
        end
        
        def vender(ip)

            ret=false

            if !@encarcelado
                if existeLaPropiedad(ip)
                    if @propiedades[ip].vender(self)
                        @propiedades.delete_at(ip)
                        ret=true
                        Diario.instance.ocurre_evento('Se ha vendido una propiedad.')
                    end
                end
            end

            return ret

        end
         
        #PROTECTED Y PRIVADO DE LOS CONSULTORES

        private :CasasMax, :HotelesMax, :PrecioLibertad, :PremioPasoSalida
        
        #PROTECTED Y PRIVADO DE LOS METODOS

        private :existeLaPropiedad, :perderSalvoConducto, :puedeSalirCarcelPagando, :puedoEdificarCasa
        private :puedoEdificarHotel, :puedoGastar
        protected :debeSerEncarcelado
    end
end
