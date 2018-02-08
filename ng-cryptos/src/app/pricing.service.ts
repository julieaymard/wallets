import {Injectable} from '@angular/core';
import {Coin} from "./model/coins";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class PricingService {
// asynchr
  coins: Coin[];

  constructor(public http: HttpClient) {

  }

  getColor(symbol) {
    if (symbol === 'USD') return 'black'

    let upCoin = this.coins
      .find(coin => coin.symbol === symbol)
    if (upCoin.up === true) {
      return 'green'
    } else {
      return 'red'
    }
  }


  loadPrices() {
    let url = 'https://api.coinmarketcap.com/v1/ticker/?limit=10';
    console.log('load >>', url)

    function mapper(coin) {
      return {
        name: coin.name,
        symbol: coin.symbol,
        price: coin.price_usd,
        up: coin.percent_change_1h > 0 ? true : false

      }
    }

    return this.http.get(url)
      .toPromise()
      .then(internetCoins => (internetCoins as any).map(mapper))
      .then(joliCoins => {
        this.coins = joliCoins;
        return joliCoins;

      });
  }


  priceToDollar(quantity, symbol) {
   // console.log(quantity, symbol);
    for (let i = 0; i < this.coins.length; i++) {

      let coin = this.coins[i];
      if (coin.symbol == symbol) {
        //console.log('I do have', coin);
        return (quantity * coin.price);
      }
    }


  }
}



