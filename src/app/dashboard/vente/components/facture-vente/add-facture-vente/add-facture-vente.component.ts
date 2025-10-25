import { CommonModule } from '@angular/common';
import { Component, inject, Inject } from '@angular/core';
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
    selector: 'app-add-facture-vente',
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
    templateUrl: './add-facture-vente.component.html',
    styleUrls: ['./add-facture-vente.component.scss']
})
export class AddFactureVenteComponent {
    private fb = inject(FormBuilder);
    private factureVenteService = inject(FactureVenteService);
    private clientService = inject(ClientService);
    private dialogRef = inject(MatDialogRef<AddFactureVenteComponent>);

    factureVenteForm: FormGroup;
    clients: Client[] = [];
    devises = ['EUR', 'USD', 'TND'];

    constructor(@Inject(MAT_DIALOG_DATA) public data: FactureVente) {
        this.factureVenteForm = this.fb.group({
            serie: ['', Validators.required],
            date: ['', Validators.required],
            heurePublication: [''],
            modifierDateHeurePublication: [false],
            dateEcheance: [''],
            estPaye: [false],
            estRetour: [false],
            appliquerRetenueImpot: [false],
            numeroFactureFournisseur: [''],
            dateFactureFournisseur: [''],
            centreDeCouts: [''],
            projet: [''],
            devise: [''],
            numero: [''],
            montantTtc: [0],
            client: [null] // champ non obligatoire
            // Ajouter ici si besoin :
            // societe: [''],
            // paiements: this.fb.array([])
        });
        this.loadClients();
    }

    ngOnInit(): void {
        if (this.data) {
            this.factureVenteForm.patchValue(this.data);
        }
    }

    loadClients() {
        this.clientService.getAllClients().subscribe({
            next: (data: Client[]) => { this.clients = data; },
            error: (err: any) => console.error('Error loading clients:', err)
        });
    }

    onSubmit() {
        if (this.factureVenteForm.valid) {
            this.factureVenteService.createFactureVente(this.factureVenteForm.value).subscribe({
                next: (response) => { this.dialogRef.close(response); },
                error: (error) => { console.error('Error creating facture vente:', error); }
            });
        }
    }

    onCancel() {
        this.dialogRef.close();
    }
} 