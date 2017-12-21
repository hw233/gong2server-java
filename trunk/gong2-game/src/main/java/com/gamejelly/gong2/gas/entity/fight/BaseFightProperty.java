package com.gamejelly.gong2.gas.entity.fight;

import com.gamejelly.gong2.gas.entity.ExtraAddProp;

public abstract class BaseFightProperty extends ExtraAddProp {

	public abstract int getLv();

	public long totAtk;
	public float totPreAtk;
	public long totDef;
	public float totPreDef;
	public long totDpower;
	public float totPreDpower;
	public long totHp;
	public float totPreHp;
	public long totCp;
	public float totCri;
	public float totDcri;
	public long totFocus;
	public float totDparry;
	public float totParry;
	public long totTen;
	public long totPen;
	public long totDeHIT;
	public long totEahit;
	public float totPreDeepen;
	public float totPreEase;

	public long criProb;
	public long parryProb;

	public long finalAtk;
	public long finalDef;
	public long finalDpower;
	public long finalHp;
	public long finalCp;
	public long finalPen;
	public long finalDeHIT;
	public long finalEahit;
	public float finalPreDeepen;
	public float finalPreEase;

	public long getTotAtk() {

		return totAtk;
	}

	public void setTotAtk(long totAtk) {

		this.totAtk = totAtk;
	}

	public float getTotPreAtk() {

		return totPreAtk;
	}

	public void setTotPreAtk(float totPreAtk) {

		this.totPreAtk = totPreAtk;
	}

	public long getTotDef() {

		return totDef;
	}

	public void setTotDef(long totDef) {

		this.totDef = totDef;
	}

	public float getTotPreDef() {

		return totPreDef;
	}

	public void setTotPreDef(float totPreDef) {

		this.totPreDef = totPreDef;
	}

	public long getTotDpower() {

		return totDpower;
	}

	public void setTotDpower(long totDpower) {

		this.totDpower = totDpower;
	}

	public float getTotPreDpower() {

		return totPreDpower;
	}

	public void setTotPreDpower(float totPreDpower) {

		this.totPreDpower = totPreDpower;
	}

	public long getTotHp() {

		return totHp;
	}

	public void setTotHp(long totHp) {

		this.totHp = totHp;
	}

	public float getTotPreHp() {

		return totPreHp;
	}

	public void setTotPreHp(float totPreHp) {

		this.totPreHp = totPreHp;
	}

	public long getTotCp() {

		return totCp;
	}

	public void setTotCp(long totCp) {

		this.totCp = totCp;
	}

	public void setTotDcri(long totDcri) {

		this.totDcri = totDcri;
	}

	public long getTotFocus() {

		return totFocus;
	}

	public void setTotFocus(long totFocus) {

		this.totFocus = totFocus;
	}

	public void setTotParry(long totParry) {

		this.totParry = totParry;
	}

	public long getTotTen() {

		return totTen;
	}

	public void setTotTen(long totTen) {

		this.totTen = totTen;
	}

	public long getTotPen() {

		return totPen;
	}

	public void setTotPen(long totPen) {

		this.totPen = totPen;
	}

	public long getTotDeHIT() {

		return totDeHIT;
	}

	public void setTotDeHIT(long totDeHIT) {

		this.totDeHIT = totDeHIT;
	}

	public long getTotEahit() {

		return totEahit;
	}

	public void setTotEahit(long totEahit) {

		this.totEahit = totEahit;
	}

	public float getTotPreDeepen() {

		return totPreDeepen;
	}

	public void setTotPreDeepen(float totPreDeepen) {

		this.totPreDeepen = totPreDeepen;
	}

	public float getTotPreEase() {

		return totPreEase;
	}

	public void setTotPreEase(float totPreEase) {

		this.totPreEase = totPreEase;
	}

	public long getCriProb() {

		return criProb;
	}

	public void setCriProb(long criProb) {

		this.criProb = criProb;
	}

	public long getParryProb() {

		return parryProb;
	}

	public void setParryProb(long parryProb) {

		this.parryProb = parryProb;
	}

	public long getFinalAtk() {

		return finalAtk;
	}

	public void setFinalAtk(long finalAtk) {

		this.finalAtk = finalAtk;
	}

	public long getFinalDef() {

		return finalDef;
	}

	public void setFinalDef(long finalDef) {

		this.finalDef = finalDef;
	}

	public long getFinalDpower() {

		return finalDpower;
	}

	public void setFinalDpower(long finalDpower) {

		this.finalDpower = finalDpower;
	}

	public long getFinalHp() {

		return finalHp;
	}

	public void setFinalHp(long finalHp) {

		this.finalHp = finalHp;
	}

	public long getFinalPen() {

		return finalPen;
	}

	public void setFinalPen(long finalPen) {

		this.finalPen = finalPen;
	}

	public long getFinalDeHIT() {

		return finalDeHIT;
	}

	public void setFinalDeHIT(long finalDeHIT) {

		this.finalDeHIT = finalDeHIT;
	}

	public long getFinalEahit() {

		return finalEahit;
	}

	public void setFinalEahit(long finalEahit) {

		this.finalEahit = finalEahit;
	}

	public float getFinalPreDeepen() {

		return finalPreDeepen;
	}

	public void setFinalPreDeepen(float finalPreDeepen) {

		this.finalPreDeepen = finalPreDeepen;
	}

	public float getFinalPreEase() {

		return finalPreEase;
	}

	public void setFinalPreEase(float finalPreEase) {

		this.finalPreEase = finalPreEase;
	}

	public float getTotCri() {

		return totCri;
	}

	public void setTotCri(float totCri) {

		this.totCri = totCri;
	}

	public float getTotDcri() {

		return totDcri;
	}

	public void setTotDcri(float totDcri) {

		this.totDcri = totDcri;
	}

	public float getTotDparry() {

		return totDparry;
	}

	public void setTotDparry(float totDparry) {

		this.totDparry = totDparry;
	}

	public float getTotParry() {

		return totParry;
	}

	public void setTotParry(float totParry) {

		this.totParry = totParry;
	}

	public long getFinalCp() {
	
		return finalCp;
	}

	public void setFinalCp(long finalCp) {
	
		this.finalCp = finalCp;
	}

}
