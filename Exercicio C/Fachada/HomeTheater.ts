import { PlayerDeMusica } from "./PlayerDeMusica";
import { TV } from "./TV";

export class HomeTheater {
  public tv: TV;
  public player: PlayerDeMusica;
  
  constructor() {
    this.tv = new TV();
    this.player = new PlayerDeMusica();
  }

  assistirFilme(filme: string) {
    const tv = new TV();
    tv.ligar();
    const filmesDisponiveis = tv.listarFilmes();

    if (filmesDisponiveis.includes(filme)) {
      tv.assistirFilme(filme);
    } else {
      console.log("Filme não disponível");
    }

    tv.desligar();
  }

  ouvirMusica(musica: string) {
    const player = new PlayerDeMusica();
    player.ligar();
    const musicasDisponiveis = player.listarMusicas();

    if (musicasDisponiveis.includes(musica)) {
      player.tocarMusica(musica);
    } else {
      console.log("Música não disponível");
    }

    player.desligar();
  }
}
