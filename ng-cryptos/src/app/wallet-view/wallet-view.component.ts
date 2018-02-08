import {Component, OnInit} from '@angular/core';
import {Wallet} from "../model/wallet";
import {PricingService} from "../pricing.service";

@Component({
  selector: 'app-wallet-view',
  templateUrl: './wallet-view.component.html',
  styleUrls: ['./wallet-view.component.css']
})
export class WalletViewComponent implements OnInit {

  wallet = new Wallet()


  constructor(public pricingService: PricingService) {
    this.wallet.pricingService = pricingService;
    pricingService.loadPrices()
      .then(data => console.log('>>>>>', data))
      .then( () => this.initWallet() );
  }

  ngOnInit() {
  }

  loadPrices(){
this.wallet.pricingService.loadPrices();
  }

  deposit(value: string) {
    let money = parseFloat(value);
    if (money > 0) {
      this.wallet.deposit(money);
    }
  }

  buy(quantity: string, symbol: string) {
    let stock = parseFloat(quantity);
    if (stock > 0) {
      this.wallet.buy(stock, symbol);
      console.log(this.wallet.lines);
    }
  }

  sell(stock: string, symbol : string){
    let sellstock = parseFloat(stock);
    if (sellstock> 0){
      this.wallet.sell(sellstock, symbol);
    }
  }

  getColor(symbol) {
    return this.pricingService.getColor(symbol);
  }

initWallet() {
  this.wallet.deposit(10000)
  this.wallet .buy (10, 'XRP')
  this.wallet .buy (1, 'BTC')

  }
}

