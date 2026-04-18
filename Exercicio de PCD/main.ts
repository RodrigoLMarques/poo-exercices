interface Observer {
  update(subject: Subject): void;
}

abstract class Subject {
  private observers: Observer[] = [];

  addObserver(observer: Observer): void {
    this.observers.push(observer);
  }

  removeObserver(observer: Observer): void {
    this.observers = this.observers.filter((o) => o !== observer);
  }

  protected notifyObservers(): void {
    this.observers.forEach((observer) => {
      observer.update(this);
    });
  }
}

interface RiverData {
  temperature: number;
  atmosphericPressure: number;
  ph: number;
  humidity: number;
}

class PCD extends Subject {
  private name: string;
  private data: RiverData;

  constructor(name: string) {
    super();
    this.name = name;
    this.data = {
      temperature: 0,
      atmosphericPressure: 0,
      ph: 7,
      humidity: 0,
    };
  }

  getData(): RiverData {
    return { ...this.data };
  }
  getName(): string {
    return this.name;
  }

  updateData(newData: Partial<RiverData>): void {
    this.data = { ...this.data, ...newData };
    this.notifyObservers();
  }
}

class University implements Observer {
  private monitoredPCDs: PCD[] = [];

  constructor(public readonly name: string) {}

  monitorPCD(pcd: PCD): void {
    this.monitoredPCDs.push(pcd);
    pcd.addObserver(this);
  }

  stopMonitoring(pcd: PCD): void {
    this.monitoredPCDs = this.monitoredPCDs.filter((p) => p !== pcd);
    pcd.removeObserver(this);
  }

  update(subject: Subject): void {
    const pcd = subject as PCD;
    const data = pcd.getData();
    console.log(`\n${this.name} - Atualização de ${pcd.getName()}`);
    console.log(`Temperatura: ${data.temperature}°C`);
    console.log(`Pressão atmosférica: ${data.atmosphericPressure} hPa`);
    console.log(`pH: ${data.ph}`);
    console.log(`Umidade: ${data.humidity}%`);
  }
}

