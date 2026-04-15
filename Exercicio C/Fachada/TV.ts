export class TV {
  ligar() {
    console.log("TV ligada");
  }

  listarFilmes() {
    return ["Filme 1", "Filme 2", "Filme 3"];
  }

  assistirFilme(filme: string) {
    console.log(`Assistindo ${filme}`);
  }

  desligar() {
    console.log("TV desligada");
  }
}