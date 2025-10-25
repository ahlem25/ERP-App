import { CommonModule } from '@angular/common';
import { Component, Inject, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FactureVenteService } from '../../../../achat/services/facture-vente.service';
import { FactureVente } from '../../../../achat/models/facture-vente.model';
import { ClientService } from '../../../../achat/services/client.service';
import { Client } from '../../../../achat/models/client.model';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-edit-facture-vente',
    standalone: true,
    imports: [
        MatIcon,
        CommonModule,
        MatCardModule,
        MatButtonModule,
        MatDialogModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatCheckboxModule,
        MatDatepickerModule,
        MatNativeDateModule,
        ReactiveFormsModule
    ],
    templateUrl: './edit-facture-vente.component.html',
    styleUrls: ['./edit-facture-vente.component.scss']
})
export class EditFactureVenteComponent {
    private fb = inject(FormBuilder);
    private dialogRef = inject(MatDialogRef<EditFactureVenteComponent>);
    private factureVenteService = inject(FactureVenteService);
    private clientService = inject(ClientService);

    factureVenteForm: FormGroup;
    clients: Client[] = [];
    devises = ['EUR', 'USD', 'TND'];

    constructor(@Inject(MAT_DIALOG_DATA) public data: FactureVente) {
        this.factureVenteForm = this.fb.group({
            serie: [data.serie, Validators.required],
            date: [data.date, Validators.required],
            heurePublication: [data.heurePublication],
            modifierDateHeurePublication: [data.modifierDateHeurePublication],
            dateEcheance: [data.dateEcheance],
            estPaye: [data.estPaye],
            estRetour: [data.estRetour],
            appliquerRetenueImpot: [data.appliquerRetenueImpot],
            numeroFactureFournisseur: [data.numeroFactureFournisseur],
            dateFactureFournisseur: [data.dateFactureFournisseur],
            centreDeCouts: [data.centreDeCouts],
            projet: [data.projet],
            devise: [data.devise],
            numero: [data.numero],
            montantTtc: [data.montantTtc],
            client: [data.client, Validators.required]
        });
        this.loadClients();
    }

    loadClients() {
        this.clientService.getAllClients().subscribe({
            next: (data: Client[]) => { this.clients = data; },
            error: (err: any) => console.error('Error loading clients:', err)
        });
    }

    onSubmit() {
        if (this.factureVenteForm.valid) {
            const updatedFacture: FactureVente = { ...this.data, ...this.factureVenteForm.value };
            this.factureVenteService.updateFactureVente(updatedFacture).subscribe({
                next: () => {
                    this.dialogRef.close(true);
                },
                error: (error: any) => {
                    console.error('Erreur lors de la modification de la facture vente', error);
                }
            });
        }
    }

    onCancel() {
        this.dialogRef.close();
    }
} 