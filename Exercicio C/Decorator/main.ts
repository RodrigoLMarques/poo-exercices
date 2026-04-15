interface Drink {
  getPrice(): number;
}

export class Coffee implements Drink {
  getPrice(): number {
    return 5;
  }
}

export class Cappuccino implements Drink {
  getPrice(): number {
    return 7;
  }
}

export class Tea implements Drink {
  getPrice(): number {
    return 3;
  }
}

export class DrinkDecorator implements Drink {
  protected drink: Drink;

  constructor(drink: Drink) {
    this.drink = drink;
  }

  getPrice(): number {
    return this.drink.getPrice();
  }
}

export class MilkDecorator extends DrinkDecorator {
  constructor(drink: Drink) {
    super(drink);
  }
  getPrice(): number {
    return super.getPrice() + 2;
  }
}

export class ChantillyDecorator extends DrinkDecorator {
  constructor(drink: Drink) {
    super(drink);
  }
  getPrice(): number {
    return super.getPrice() + 3;
  }
}

export class CinnamonDecorator extends DrinkDecorator {
  constructor(drink: Drink) {
    super(drink);
  }
  getPrice(): number {
    return super.getPrice() + 1;
  }
}

export class ChocolateSyrupDecorator extends DrinkDecorator {
  constructor(drink: Drink) {
    super(drink);
  }
  getPrice(): number {
    return super.getPrice() + 4;
  }
}

export class UnitTest {
  public static run() {
    const coffee = new Coffee();
    console.log(`Preço do café: ${coffee.getPrice()}`);

    const cappuccino = new Cappuccino();
    console.log(`Preço do cappuccino: ${cappuccino.getPrice()}`);

    const tea = new Tea();
    console.log(`Preço do chá: ${tea.getPrice()}`);

    const coffeeWithMilk = new MilkDecorator(coffee);
    console.log(`Preço do café com leite: ${coffeeWithMilk.getPrice()}`);

    const cappuccinoWithChantilly = new ChantillyDecorator(cappuccino);
    console.log(
      `Preço do cappuccino com chantilly: ${cappuccinoWithChantilly.getPrice()}`,
    );

    const teaWithCinnamon = new CinnamonDecorator(tea);
    console.log(`Preço do chá com canela: ${teaWithCinnamon.getPrice()}`);

    const coffeeWithChocolateSyrup = new ChocolateSyrupDecorator(coffee);
    console.log(
      `Preço do café com calda de chocolate: ${coffeeWithChocolateSyrup.getPrice()}`,
    );

    const cappuccinoAllDecorated = new ChantillyDecorator(
      new MilkDecorator(
        new CinnamonDecorator(new ChocolateSyrupDecorator(cappuccino)),
      ),
    );
    console.log(
      `Preço do cappuccino com leite, chantilly, canela e calda de chocolate: ${cappuccinoAllDecorated.getPrice()}`,
    );
  }
}

UnitTest.run();
