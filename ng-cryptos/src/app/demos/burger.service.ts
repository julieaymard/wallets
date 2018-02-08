import {Injectable} from '@angular/core';
import {SteackService} from "./steack.service";
import {BunService} from "./bun.service";

@Injectable()
export class BurgerService {

  constructor(public bunService: BunService, public steackService: SteackService) {
  }

  getPrice() {
    return 10;
  }
}
