module Civitas
  require_relative "Casilla.rb"
  require_relative "Casilla_Calle.rb"
  require_relative "Casilla_Impuesto.rb"
  require_relative "Casilla_Juez.rb"
  require_relative "Casilla_Sorpresa.rb"
  require_relative "CivitasJuego.rb"
  require_relative "Controlador.rb"
  require_relative "Dado.rb"
  require_relative "Diario.rb"
  require_relative "EstadosJuego.rb"
  require_relative "GestionesInmobiliarias.rb"
  require_relative "GestorEstados.rb"
  require_relative "Jugador.rb"
  require_relative "JugadorEspeculador.rb"
  require_relative "MazoSorpresas.rb"
  require_relative "OperacionesJuego.rb"
  require_relative "OperacionInmobiliaria.rb"
  require_relative "Respuestas.rb"
  require_relative "SalidasCarcel.rb"
  require_relative "Sorpresa.rb"
  require_relative "Sorpresa_IrCarcel.rb"
  require_relative "Sorpresa_IrCasilla.rb"
  require_relative "Sorpresa_JugadorEspeculador.rb"
  require_relative "Sorpresa_PagarCobrar.rb"
  require_relative "Sorpresa_PorCasaHotel.rb"
  require_relative "Sorpresa_PorJugador.rb"
  require_relative "Sorpresa_SalirCarcel.rb"
  require_relative "Tablero.rb"
  require_relative "TituloPropiedad.rb"
  require_relative "vista_textual.rb"
  require 'io/console'

  vista=Vista_textual.new
  
  dado=Dado.instance
  diario=Diario.instance

  nombres=['GONZ', 'ADRI', 'JUANMI ', 'LUIS']

  juego=CivitasJuego.new(nombres)
    

  dado.setDebug(true)

  controlador=Controlador.new(juego,vista)

  controlador.juega()

end