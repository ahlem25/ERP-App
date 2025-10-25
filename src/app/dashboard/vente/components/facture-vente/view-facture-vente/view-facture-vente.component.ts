import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FactureVente } from '../../../../achat/models/facture-vente.model';

@Component({
    selector: 'app-view-facture-vente',
    standalone: true,
    imports: [CommonModule, MatCardModule, MatButtonModule, MatDialogModule],
    templateUrl: './view-facture-vente.component.html',
    styleUrls: ['./view-facture-vente.component.scss']
})
export class ViewFactureVenteComponent {
    constructor(
        public dialogRef: MatDialogRef<ViewFactureVenteComponent>,
        @Inject(MAT_DIALOG_DATA) public data: FactureVente
    ) { }

    onCancel(): void {
        this.dialogRef.close();
    }
} 