import {PricingService} from "../pricing.service";
import {User} from "./user";

export class Line {
  value: number;

  constructor(public symbol: string, public quantity: number) {

  }
}
export class Wallet {
user? : User;
lines: Line[] = [];
  name:string;
  pricingService: PricingService;

  deposit(dollars: number) {
    let usdLine = this.lines.find(Line => Line.symbol === 'USD');
    if (usdLine === undefined) {
      this.lines.push(new Line('USD', dollars));
    } else {
      usdLine.quantity += dollars;
    }
  }

  buy(quantity: number, symbol: string) {
    //USD
    let line = this.lines.find(coin => coin.symbol === 'USD');
    let dollarAmount = line.quantity;
    let coinAmount = this.pricingService.priceToDollar(quantity, symbol);
    console.log(quantity, symbol, '>>', coinAmount, ' dollars');

    line.quantity = dollarAmount - coinAmount;

    let symbolLine = this.lines.find(line => line.symbol === symbol);
    if (symbolLine === undefined) {

      let l = new Line(symbol, quantity);
      l.value = coinAmount;
      this.lines.push(l);


    } else {
      symbolLine.quantity += quantity;
      symbolLine.value += coinAmount;
    }


  }

  sell(quantity: number, symbol: string) {
    let line = this.lines.find(coin => coin.symbol === 'USD');
    let dollarAmount = line.quantity;
    let coinAmount = this.pricingService.priceToDollar(quantity, symbol);
    console.log(quantity, symbol, '>>', coinAmount, ' dollars');

    line.quantity = dollarAmount + coinAmount;
    let symbolLine = this.lines.find(line => line.symbol === symbol);
    if (symbolLine === undefined) {
      let l = new Line(symbol, quantity);
      l.value = coinAmount;
      this.lines.push(l);
    } else {
      symbolLine.quantity -= quantity;
      symbolLine.value -= coinAmount;
    }
  }

  totalDollarValue(): number {
    let total = 0;
    for (let i = 0; i < this.lines.length; i++) {
      let line = this.lines[i];
      if (line.symbol === 'USD') {
        total = total + line.quantity;
      } else {
        total = total + this.pricingService.priceToDollar(line.quantity, line.symbol);
      }

    }
    return total;
  }

//   this.lines.reduce(function (total, line){
//   return line.symbol ===‘USD’ ?
//     total +line.quantity :
//     total+priceToDollar(line.quantity, line.symbol);
// }, 0);
}
