import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Product } from 'src/model/product.model';
import { FormGroup } from '@angular/forms';
import { ProductService } from 'src/service/product.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ProductCategory } from 'src/model/product-category.model';
import { ProductConfirm } from 'src/model/ProductConfirm.model';
import { FileUpload } from 'src/model/fileupload';
import { UploadFileService } from 'src/service/upload-file.service';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.css']
})
export class ProductManagementComponent implements OnInit {

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();
  closeResult: string;
  products: Product[];

  model = new Product();
  productIns = new Product();
  productForm: FormGroup;
  product = new ProductConfirm();

  productIns1 = new Product();
  model1 = new Product();
  submitted1 = false;
  newForm: FormGroup;

  selectedFiles: FileList;
  currentFileUpload: FileUpload;
  progress: { percentage: number } = { percentage: 0 };

  url = '';
  eventOnselectImage:any;
  downloadStr='';

  constructor(private uploadService: UploadFileService, private productService: ProductService, private modalService: NgbModal) { }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 8,
      ordering: true,
      retrieve: true,
      autoWidth: true
    };
    this.productService.getProductsList()
      .subscribe(data => {
        this.products = data;
        // console.log(this.products);
        this.dtTrigger.next();
      }, error => console.log(error));
  }

  reloadProduct() {
    let pros: Product[];
    this.products = pros;
    this.productService.getProductsList().subscribe(data => {
      this.products = data;
    }, error => console.log(error));
  }

  onDeleteProduct(key: any) {
    // console.log(key);

    this.productService.deleteProduct(key)
      .subscribe(data => console.log(data), error => console.log(error));

    this.reloadProduct();
  }

  onUpdate(content: any, id: any) {
    this.getProductById(id);
    this.open(content);
  }

  getProductById(id: any) {
    this.productService.getProduct(id)
      .subscribe(data => {
        let Oject = Object.entries(data[0]);
        // console.log(data);
        this.product.id = Number(Oject[0][1]);
        this.product.name = String(Oject[1][1]);
        this.product.productCategoryId = Number(Oject[2][1]["id"]);
        this.product.productCategoryName = String(Oject[2][1]["name"]);
        this.product.price = Number(Oject[3][1]);
        this.product.desc = String(Oject[4][1]);
        this.product.imagelink = String(Oject[5][1]);
      }, error => console.log(error));
  }

  onUpdateSubmit() {
    this.selectFile(this.eventOnselectImage);
    this.upload();
    this.callUpdateToServer();
  }

  callUpdateToServer(){
    if(this.downloadStr!=''){
      this.product.imagelink=this.downloadStr;
      console.log(this.product.imagelink);
    }
    this.productService.updateProduct(this.product.id,
      {
        name: this.product.name,
        //edit them cho nay tiep
        productCategory:
        {
          id: this.product.productCategoryId,
          name: this.product.productCategoryName
        },
        price: this.product.price,
        desc: this.product.desc,
        imagelink: this.product.imagelink,
      }
    ).subscribe(data => {
      console.log('edit success');
      this.productIns = data as Product;
    }, error => console.log(error));

    this.reloadProduct();
    this.modalService.dismissAll();
  }

  open(content) {
    // console.log(content);
    this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
      size: 'lg'
    }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  save() {
    try {
      if (this.model) {
        this.productIns1.name = this.model1.name;
      }
    } catch (error) {
      console.log(error);
    }


    try {
      this.productService.createProduct(this.productIns1)
        .subscribe(data => console.log(data), error => console.log(error));
    } catch (error) {
      console.log(error);
    }

    this.model1 = new Product();
    this.productIns1 = new Product();
  }

  onSubmit() {
    this.submitted1 = true;
    this.save();
    this.reloadProduct();
    this.modalService.dismissAll();
  }

  selectFile(event) {
    const file = event.target.files.item(0);

    if (file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }

  upload() {
    const file = this.selectedFiles.item(0);
    this.selectedFiles = undefined;

    this.currentFileUpload = new FileUpload(file);
    
    const wait = (ms) => new Promise(res => setTimeout(res, ms));
    // this.downloadStr= this.waitFunct();

    this.downloadStr = (ms) => new Promise(res => setTimeout(res, ms));

    this.eventOnselectImage=undefined;
    this.url='';
  }

  waitFunct(){
    return this.uploadService.pushFileToStorage(this.currentFileUpload, this.progress)
    
  }
  
  onSelectFile(event) {
    this.eventOnselectImage= event;
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url

      reader.onload = (event) => { // called once readAsDataURL is completed
        // console.log(event)
        // this.url = event.target.result;
        this.url=String(reader.result);
      }
    }
  }
}
