
export class PlayerDeMusica {
  ligar() {
    console.log("Player de música ligado");
  }

  listarMusicas() {
    return ["Música 1", "Música 2", "Música 3"];
  }

  tocarMusica(musica: string) {
    console.log(`Tocando ${musica}`);
  }

  desligar() {
    console.log("Player de música desligado");
  }
}