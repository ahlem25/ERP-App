import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatIcon } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';

import { AddFactureVenteComponent } from '../add-facture-vente/add-facture-vente.component';
import { EditFactureVenteComponent } from '../edit-facture-vente/edit-facture-vente.component';
import { ViewFactureVenteComponent } from '../view-facture-vente/view-facture-vente.component';
import { MatLabel } from '@angular/material/form-field';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FeathericonsModule } from '../../../../../icons/feathericons/feathericons.module';
import { FactureVenteService } from '../../../../achat/services/facture-vente.service';
import { FactureVente } from '../../../../achat/models/facture-vente.model';

@Component({
    selector: 'app-list-facture-vente',
    standalone: true,
    imports: [
        FeathericonsModule,
        MatInputModule,
        MatFormFieldModule,
        MatLabel,
        CommonModule,
        MatCardModule,
        MatButtonModule,
        MatMenuModule,
        MatPaginatorModule,
        MatTableModule,
        MatIcon,
        MatTooltipModule
    ],
    templateUrl: './list-facture-vente.component.html',
    styleUrls: ['./list-facture-vente.component.scss']
})
export class ListFactureVenteComponent {
    dialog = inject(MatDialog);
    factureVenteService = inject(FactureVenteService);
    facturesVente: FactureVente[] = [];

    displayedColumns: string[] = [
        'serie',
        'date',
        'numero',
        'montantTtc',
        'client',
        'dateEcheance',
        'estPaye',
        'estRetour',
        'appliquerRetenueImpot',
        'numeroFactureFournisseur',
        'dateFactureFournisseur',
        'centreDeCouts',
        'projet',
        'devise',
        'societe',
        'paiements',
        'action'
    ];

    dataSource = new MatTableDataSource<FactureVente>([]);

    @ViewChild(MatPaginator) paginator: MatPaginator;

    ngOnInit() {
        this.loadFacturesVente();
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
    }

    loadFacturesVente() {
        this.factureVenteService.getAllFactureVentes().subscribe({
            next: (data: FactureVente[]) => {
                this.facturesVente = data;
                this.dataSource = new MatTableDataSource(this.facturesVente);
                if (this.paginator) {
                    this.dataSource.paginator = this.paginator;
                }
            },
            error: (err: any) => console.error('Error loading FacturesVente:', err),
        });
    }

    deleteFactureVente(element: FactureVente) {
        this.alertConfirmation().then((result) => {
            if (result.isConfirmed) {
                if (element.id != null) {
                    this.factureVenteService.deleteFactureVente(element.id).subscribe({
                        next: () => {
                            this.loadFacturesVente();
                        },
                        error: (err: any) => {
                            console.log('Erreur suppression', err);
                        },
                    });
                } else {
                    console.warn('ID de la FactureVente est undefined ou null.');
                }
            }
        });
    }

    alertConfirmation() {
        return Swal.fire({
            title: 'Êtes-vous sûr ?',
            text: 'Voulez-vous supprimer cette Facture Vente ?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Oui',
            cancelButtonText: 'Non',
            confirmButtonColor: '#00A2E9',
            cancelButtonColor: '#C4C4C4',
        });
    }

    openAddFactureVenteDialog(): void {
        const dialogRef = this.dialog.open(AddFactureVenteComponent);
        dialogRef.afterClosed().subscribe((result: any) => {
            if (result) {
                this.loadFacturesVente();
            }
        });
    }

    openEditFactureVenteDialog(element: FactureVente): void {
        const dialogRef = this.dialog.open(EditFactureVenteComponent, {
            width: '600px',
            data: { ...element },
        });
        dialogRef.afterClosed().subscribe((result: any) => {
            if (result) {
                this.loadFacturesVente();
            }
        });
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

    openViewFactureVenteDialog(element: FactureVente): void {
        this.dialog.open(ViewFactureVenteComponent, {
            width: '600px',
            data: { ...element }
        });
    }
} 